package individualassignment.edubridge.business.users.student.impl;

import individualassignment.edubridge.business.users.CountryIdValidator;
import individualassignment.edubridge.business.users.exceptions.UserNameAlreadyExistsException;
import individualassignment.edubridge.business.users.student.exceptions.StudentNameAlreadyExistsException;
import individualassignment.edubridge.domain.users.requests.CreateStudentRequest;
import individualassignment.edubridge.domain.users.responses.CreateStudentResponse;
import individualassignment.edubridge.persistence.address.entities.CountryEntity;
import individualassignment.edubridge.persistence.users.StudentRepository;
import individualassignment.edubridge.persistence.users.UserRepository;
import individualassignment.edubridge.persistence.users.entities.StudentEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateStudentUseCaseImplTest {

    @Mock
    private StudentRepository studentRepositoryMock;
    @Mock
    private CountryIdValidator countryIdValidatorMock;
    @Mock
    private PasswordEncoder passwordEncoderMock;
    @Mock
    private UserRepository userRepositoryMock;
    @InjectMocks
    private CreateStudentUseCaseImpl createStudentUseCase;

    @Test
    void createStudent_NewStudent_ShouldReturnCreatedStudentId() {
        CreateStudentRequest request = CreateStudentRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .countryId(1L)
                .password("password123")
                .userName("johndoe")
                .build();

        when(userRepositoryMock.existsByUserName(request.getUserName())).thenReturn(false);
        when(studentRepositoryMock.existsByFirstNameAndLastName(request.getFirstName(), request.getLastName())).thenReturn(false);
        doNothing().when(countryIdValidatorMock).validateId(request.getCountryId());
        when(passwordEncoderMock.encode(request.getPassword())).thenReturn("encodedPassword");

        StudentEntity savedStudent = StudentEntity.builder()
                .id(1L)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .favoriteCourses(Collections.emptyList())
                .country(CountryEntity.builder().id(1L).code("NL").name("Netherlands").build())
                .build();

        when(studentRepositoryMock.save(any(StudentEntity.class))).thenReturn(savedStudent);

        CreateStudentResponse response = createStudentUseCase.createStudent(request);

        assertNotNull(response);
        assertEquals(savedStudent.getId(), response.getStudentId());
    }

    @Test
    void createStudent_ExistingUserName_ShouldThrowUserNameAlreadyExistsException() {
        CreateStudentRequest request = CreateStudentRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .countryId(1L)
                .password("password123")
                .userName("johndoe")
                .build();

        when(userRepositoryMock.existsByUserName(request.getUserName())).thenReturn(true);

        assertThrows(UserNameAlreadyExistsException.class, () -> createStudentUseCase.createStudent(request));
    }

    @Test
    void createStudent_ExistingStudentName_ShouldThrowStudentNameAlreadyExistsException() {
        CreateStudentRequest request = CreateStudentRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .countryId(1L)
                .password("password123")
                .userName("johndoe")
                .build();

        when(userRepositoryMock.existsByUserName(request.getUserName())).thenReturn(false);
        when(studentRepositoryMock.existsByFirstNameAndLastName(request.getFirstName(), request.getLastName())).thenReturn(true);

        assertThrows(StudentNameAlreadyExistsException.class, () -> createStudentUseCase.createStudent(request));
    }
}