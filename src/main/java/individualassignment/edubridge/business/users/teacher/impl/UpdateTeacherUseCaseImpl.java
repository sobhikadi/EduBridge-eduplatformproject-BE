package individualassignment.edubridge.business.users.teacher.impl;

import individualassignment.edubridge.business.users.CountryIdValidator;
import individualassignment.edubridge.business.users.exceptions.UnauthorizedDataAccessException;
import individualassignment.edubridge.business.users.student.exceptions.InvalidStudentException;
import individualassignment.edubridge.business.users.teacher.UpdateTeacherUseCase;
import individualassignment.edubridge.domain.users.AccessToken;
import individualassignment.edubridge.domain.users.requests.UpdateTeacherRequest;
import individualassignment.edubridge.persistence.address.entities.AddressEntity;
import individualassignment.edubridge.persistence.address.entities.CountryEntity;
import individualassignment.edubridge.persistence.users.TeacherRepository;
import individualassignment.edubridge.persistence.users.UserRepository;
import individualassignment.edubridge.persistence.users.entities.TeacherEntity;
import individualassignment.edubridge.persistence.users.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateTeacherUseCaseImpl implements UpdateTeacherUseCase {

    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    private final CountryIdValidator countryIdValidator;
    private final AccessToken requestAccessToken;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void updateTeacher(UpdateTeacherRequest request) {
        if (!Objects.equals(requestAccessToken.getTeacherId(), request.getId())) {
            throw new UnauthorizedDataAccessException();
        }
        Optional<TeacherEntity> teacherOptional = teacherRepository.findById(request.getId());
        Optional<UserEntity> userOptional = userRepository.findById(request.getId());
        if (teacherOptional.isEmpty()) {
            throw new InvalidStudentException();
        }
        if (userOptional.isEmpty()) {
            throw new InvalidStudentException();
        }
        countryIdValidator.validateId(request.getCountryId());

        TeacherEntity teacher = teacherOptional.get();
        UserEntity user = userOptional.get();
        updateTeacherFields(request, teacher);
        UpdateUserFields(request, user);
    }

    private void updateTeacherFields(UpdateTeacherRequest request, TeacherEntity teacher) {
        AddressEntity address = AddressEntity.builder()
                .street(request.getStreet())
                .city(request.getCity())
                .zipcode(request.getZipcode())
                .country(CountryEntity.builder().id(request.getCountryId()).build())
                .build();

        teacher.setFirstName(request.getFirstName());
        teacher.setLastName(request.getLastName());
        teacher.setAddress(address);
        teacherRepository.save(teacher);
    }

    private void UpdateUserFields(UpdateTeacherRequest request, UserEntity user) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        user.setUserName(request.getUserName());
        user.setPassword(encodedPassword);

        userRepository.save(user);
    }
}
