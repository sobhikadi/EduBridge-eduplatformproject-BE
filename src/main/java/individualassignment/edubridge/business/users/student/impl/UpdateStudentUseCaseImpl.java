package individualassignment.edubridge.business.users.student.impl;


import individualassignment.edubridge.business.users.CountryIdValidator;
import individualassignment.edubridge.business.users.exceptions.UnauthorizedDataAccessException;
import individualassignment.edubridge.business.users.student.UpdateStudentUseCase;
import individualassignment.edubridge.business.users.student.exceptions.InvalidStudentException;
import individualassignment.edubridge.domain.users.AccessToken;
import individualassignment.edubridge.domain.users.requests.UpdateStudentRequest;
import individualassignment.edubridge.persistence.address.entities.CountryEntity;
import individualassignment.edubridge.persistence.users.StudentRepository;
import individualassignment.edubridge.persistence.users.UserRepository;
import individualassignment.edubridge.persistence.users.entities.StudentEntity;
import individualassignment.edubridge.persistence.users.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateStudentUseCaseImpl implements UpdateStudentUseCase {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final CountryIdValidator countryIdValidator;
    private final AccessToken requestAccessToken;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void updateStudent(UpdateStudentRequest request) {

        if (!Objects.equals(requestAccessToken.getStudentId(), request.getId())) {
            throw new UnauthorizedDataAccessException();
        }

        Optional<StudentEntity> studentOptional = studentRepository.findById(request.getId());
        Optional<UserEntity> userOptional = userRepository.findById(request.getId());
        if (studentOptional.isEmpty()) {
            throw new InvalidStudentException();
        }
        if (userOptional.isEmpty()) {
            throw new InvalidStudentException();
        }
        countryIdValidator.validateId(request.getCountryId());

        StudentEntity student = studentOptional.get();
        UserEntity user = userOptional.get();
        updateStudentFields(request, student);
    }

    private void updateStudentFields(UpdateStudentRequest request, StudentEntity student) {
        student.setCountry(CountryEntity.builder().id(request.getCountryId()).build());
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());

        studentRepository.save(student);
    }

    private void UpdateUserFields(UpdateStudentRequest request, UserEntity user) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        user.setUserName(request.getUserName());
        user.setPassword(encodedPassword);

        userRepository.save(user);
    }
}
