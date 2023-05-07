package individualassignment.edubridge.business.users.teacher.impl;

import individualassignment.edubridge.business.users.CountryIdValidator;
import individualassignment.edubridge.business.users.exceptions.UnauthorizedDataAccessException;
import individualassignment.edubridge.domain.users.AccessToken;
import individualassignment.edubridge.domain.users.requests.UpdateTeacherRequest;
import individualassignment.edubridge.persistence.address.entities.AddressEntity;
import individualassignment.edubridge.persistence.address.entities.CountryEntity;
import individualassignment.edubridge.persistence.users.TeacherRepository;
import individualassignment.edubridge.persistence.users.UserRepository;
import individualassignment.edubridge.persistence.users.entities.TeacherEntity;
import individualassignment.edubridge.persistence.users.entities.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateTeacherUseCaseImplTest {

    @Mock
    private TeacherRepository teacherRepositoryMock;

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private CountryIdValidator countryIdValidatorMock;

    @Mock
    private AccessToken requestAccessTokenMock;

    @Mock
    private PasswordEncoder passwordEncoderMock;

    @InjectMocks
    private UpdateTeacherUseCaseImpl updateTeacherUseCase;

    @Test
    void updateTeacher_ValidTeacherId_ShouldUpdateTeacher() {
        long teacherId = 1L;
        TeacherEntity teacherEntity = createTeacherEntity(teacherId, "John", "Doe");
        UserEntity userEntity = createUserEntity(teacherId, "john.doe", "password123");

        UpdateTeacherRequest request = new UpdateTeacherRequest();
        request.setId(teacherId);
        request.setFirstName("John");
        request.setLastName("Smith");
        request.setStreet("New Street");
        request.setCity("New City");
        request.setZipcode("10001");
        request.setCountryId(1L);
        request.setUserName("john.smith");
        request.setPassword("new_password");

        when(requestAccessTokenMock.getTeacherId()).thenReturn(teacherId);
        when(teacherRepositoryMock.findById(teacherId)).thenReturn(Optional.of(teacherEntity));
        when(userRepositoryMock.findById(teacherId)).thenReturn(Optional.of(userEntity));
        when(passwordEncoderMock.encode(request.getPassword())).thenReturn("encoded_password");

        updateTeacherUseCase.updateTeacher(request);

        assertEquals("John", teacherEntity.getFirstName());
        assertEquals("Smith", teacherEntity.getLastName());

        verify(teacherRepositoryMock, times(1)).save(teacherEntity);
        verify(userRepositoryMock, times(1)).save(userEntity);
        verify(teacherRepositoryMock, times(1)).findById(teacherId);
        verify(userRepositoryMock, times(1)).findById(teacherId);
    }

    @Test
    void updateTeacher_InvalidTeacherId_ShouldThrowUnauthorizedDataAccessException() {
        long teacherId = 1L;

        when(requestAccessTokenMock.getTeacherId()).thenReturn(2L);

        assertThrows(UnauthorizedDataAccessException.class,
                () -> updateTeacherUseCase.updateTeacher(new UpdateTeacherRequest()));
    }

    private TeacherEntity createTeacherEntity(Long id, String firstName, String lastName) {
        AddressEntity address = AddressEntity.builder()
                .street("Main Street")
                .city("New York")
                .zipcode("12345")
                .country(CountryEntity.builder().code("NL").name("Netherlands").id(1L).build())
                .build();

        return TeacherEntity.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .address(address)
                .build();
    }

    private UserEntity createUserEntity(Long id, String userName, String password) {
        return UserEntity.builder()
                .id(id)
                .userName(userName)
                .password(password)
                .build();
    }
}
