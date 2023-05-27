package individualassignment.edubridge.business.users.student.impl;

import individualassignment.edubridge.business.users.CountryIdValidator;
import individualassignment.edubridge.business.users.exceptions.UserNameAlreadyExistsException;
import individualassignment.edubridge.business.users.student.CreateStudentUseCase;
import individualassignment.edubridge.business.users.student.exceptions.StudentNameAlreadyExistsException;
import individualassignment.edubridge.domain.users.UserRoleEnum;
import individualassignment.edubridge.domain.users.requests.CreateStudentRequest;
import individualassignment.edubridge.domain.users.responses.CreateStudentResponse;
import individualassignment.edubridge.persistence.address.entities.CountryEntity;
import individualassignment.edubridge.persistence.users.StudentRepository;
import individualassignment.edubridge.persistence.users.UserRepository;
import individualassignment.edubridge.persistence.users.entities.StudentEntity;
import individualassignment.edubridge.persistence.users.entities.UserEntity;
import individualassignment.edubridge.persistence.users.entities.UserRoleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CreateStudentUseCaseImpl implements CreateStudentUseCase {

    private final StudentRepository studentRepository;
    private final CountryIdValidator countryIdValidator;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    @Transactional
    @Override
    public CreateStudentResponse createStudent(CreateStudentRequest request) {
        if (userRepository.existsByUserName(request.getUserName())) {
            throw new UserNameAlreadyExistsException
                    ("Username already exists, please choose another one, " +
                            "or update your account to become student. " +
                            "you can do this by logging in and going to your profile.");
        }
        if (studentRepository.existsByFirstNameAndLastName(request.getFirstName(), request.getLastName())) {
            throw new StudentNameAlreadyExistsException();
        }
        countryIdValidator.validateId(request.getCountryId());

        StudentEntity savedStudent = saveNewStudent(request);

        saveNewUser(savedStudent, request.getPassword(), request.getUserName());


        return CreateStudentResponse.builder()
                .studentId(savedStudent.getId())
                .build();
    }

    private void saveNewUser(StudentEntity student, String password, String userName) {
        String encodedPassword = passwordEncoder.encode(password);

        UserEntity newUser = UserEntity.builder()
                .userName(userName)
                .password(encodedPassword)
                .student(student)
                .build();

        newUser.setUserRoles(Set.of(
                UserRoleEntity.builder()
                        .user(newUser)
                        .role(UserRoleEnum.STUDENT)
                        .build()));
        userRepository.save(newUser);
    }

    private StudentEntity saveNewStudent(CreateStudentRequest request) {
        StudentEntity newStudent = StudentEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .country(CountryEntity.builder().id(request.getCountryId()).build())
                .build();
        return studentRepository.save(newStudent);
    }
}
