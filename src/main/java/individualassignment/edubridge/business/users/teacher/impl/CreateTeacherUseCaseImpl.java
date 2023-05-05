package individualassignment.edubridge.business.users.teacher.impl;

import individualassignment.edubridge.business.users.CountryIdValidator;
import individualassignment.edubridge.business.users.exceptions.UserNameAlreadyExistsException;
import individualassignment.edubridge.business.users.teacher.CreateTeacherUseCase;
import individualassignment.edubridge.business.users.teacher.exceptions.TeacherNameAlreadyExistsException;
import individualassignment.edubridge.domain.users.UserRoleEnum;
import individualassignment.edubridge.domain.users.requests.CreateTeacherRequest;
import individualassignment.edubridge.domain.users.responses.CreateTeacherResponse;
import individualassignment.edubridge.persistence.address.entities.AddressEntity;
import individualassignment.edubridge.persistence.address.entities.CountryEntity;
import individualassignment.edubridge.persistence.users.TeacherRepository;
import individualassignment.edubridge.persistence.users.UserRepository;
import individualassignment.edubridge.persistence.users.entities.TeacherEntity;
import individualassignment.edubridge.persistence.users.entities.UserEntity;
import individualassignment.edubridge.persistence.users.entities.UserRoleEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
@AllArgsConstructor
public class CreateTeacherUseCaseImpl implements CreateTeacherUseCase {

    private final TeacherRepository teacherRepository;
    private final CountryIdValidator countryIdValidator;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public CreateTeacherResponse createTeacher(CreateTeacherRequest request) {

        if (userRepository.existsByUserName(request.getUserName())) {
            throw new UserNameAlreadyExistsException();
        }
        if (teacherRepository.existsByFirstNameAndLastName(request.getFirstName(), request.getLastName())) {
            throw new TeacherNameAlreadyExistsException();
        }
        countryIdValidator.validateId(request.getCountryId());

        TeacherEntity savedTeacher = saveNewTeacher(request);
        saveNewUser(savedTeacher, request.getPassword(), request.getUserName());

        return CreateTeacherResponse.builder()
                .teacherId(savedTeacher.getId())
                .build();
    }

    private void saveNewUser(TeacherEntity teacher, String password, String userName) {
        String encodedPassword = passwordEncoder.encode(password);

        UserEntity newUser = UserEntity.builder()
                .userName(userName)
                .password(encodedPassword)
                .teacher(teacher)
                .build();

        newUser.setUserRoles(Set.of(
                UserRoleEntity.builder()
                        .user(newUser)
                        .role(UserRoleEnum.TEACHER)
                        .build()));
        userRepository.save(newUser);
    }

    private TeacherEntity saveNewTeacher(CreateTeacherRequest request) {
        AddressEntity address = AddressEntity.builder()
                .street(request.getStreet())
                .zipcode(request.getZipcode())
                .city(request.getCity())
                .country(CountryEntity.builder()
                        .id(request.getCountryId())
                        .build())
                .build();

        TeacherEntity newTeacher = TeacherEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .address(address)
                .build();
        return teacherRepository.save(newTeacher);
    }

}
