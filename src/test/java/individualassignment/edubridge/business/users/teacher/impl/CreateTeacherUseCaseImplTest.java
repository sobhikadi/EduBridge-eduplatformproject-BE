package individualassignment.edubridge.business.users.teacher.impl;

import individualassignment.edubridge.business.users.CountryIdValidator;
import individualassignment.edubridge.business.users.exceptions.UserNameAlreadyExistsException;
import individualassignment.edubridge.business.users.teacher.exceptions.TeacherNameAlreadyExistsException;
import individualassignment.edubridge.domain.users.requests.CreateTeacherRequest;
import individualassignment.edubridge.domain.users.responses.CreateTeacherResponse;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateTeacherUseCaseImplTest {

    @Mock
    private TeacherRepository teacherRepositoryMock;
    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private CountryIdValidator countryIdValidatorMock;
    @Mock
    private PasswordEncoder passwordEncoderMock;

    @InjectMocks
    private CreateTeacherUseCaseImpl createTeacherUseCase;

    @Test
    void createTeacher_ValidRequest_ShouldCreateTeacher() {

        CreateTeacherRequest request = CreateTeacherRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .userName("johndoe")
                .password("password123")
                .countryId(1L)
                .build();

        when(userRepositoryMock.existsByUserName(request.getUserName())).thenReturn(false);
        when(teacherRepositoryMock.existsByFirstNameAndLastName(
                request.getFirstName(), request.getLastName())).thenReturn(false);
        doNothing().when(countryIdValidatorMock).validateId(request.getCountryId());
        when(passwordEncoderMock.encode(request.getPassword())).thenReturn("encodedPassword");

        TeacherEntity savedTeacher = TeacherEntity.builder()
                .id(1L)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();
        when(teacherRepositoryMock.save(any(TeacherEntity.class))).thenReturn(savedTeacher);

        CreateTeacherResponse response = createTeacherUseCase.createTeacher(request);

        assertEquals(1L, response.getTeacherId());
        verify(teacherRepositoryMock).save(any(TeacherEntity.class));
        verify(userRepositoryMock).save(any(UserEntity.class));
    }

    @Test
    void createTeacher_UserNameAlreadyExists_ShouldThrowUserNameAlreadyExistsException() {
        String userName = "JohnDoe";

        CreateTeacherRequest request = CreateTeacherRequest.builder()
                .userName(userName)
                .build();

        when(userRepositoryMock.existsByUserName(userName)).thenReturn(true);

        assertThrows(UserNameAlreadyExistsException.class, () -> createTeacherUseCase.createTeacher(request));
        verify(teacherRepositoryMock, never()).save(any(TeacherEntity.class));
        verify(userRepositoryMock, never()).save(any(UserEntity.class));
    }

    @Test
    void createTeacher_TeacherNameAlreadyExists_ShouldThrowTeacherNameAlreadyExistsException() {
        String firstName = "John";
        String lastName = "Doe";
        String userName = "JohnDoe";

        CreateTeacherRequest request = CreateTeacherRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .userName(userName)
                .build();

        when(userRepositoryMock.existsByUserName(userName)).thenReturn(false);
        when(teacherRepositoryMock.existsByFirstNameAndLastName(firstName, lastName)).thenReturn(true);

        assertThrows(TeacherNameAlreadyExistsException.class, () -> createTeacherUseCase.createTeacher(request));
        verify(teacherRepositoryMock, never()).save(any(TeacherEntity.class));
        verify(userRepositoryMock, never()).save(any(UserEntity.class));
    }
}
