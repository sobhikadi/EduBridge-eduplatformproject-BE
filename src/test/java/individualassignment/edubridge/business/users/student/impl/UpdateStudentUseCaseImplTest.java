package individualassignment.edubridge.business.users.student.impl;

import individualassignment.edubridge.business.users.CountryIdValidator;
import individualassignment.edubridge.business.users.exceptions.UnauthorizedDataAccessException;
import individualassignment.edubridge.business.users.student.exceptions.InvalidStudentException;
import individualassignment.edubridge.domain.users.AccessToken;
import individualassignment.edubridge.domain.users.requests.UpdateStudentRequest;
import individualassignment.edubridge.persistence.address.entities.CountryEntity;
import individualassignment.edubridge.persistence.users.StudentRepository;
import individualassignment.edubridge.persistence.users.UserRepository;
import individualassignment.edubridge.persistence.users.entities.StudentEntity;
import individualassignment.edubridge.persistence.users.entities.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateStudentUseCaseImplTest {

    @Mock
    private StudentRepository studentRepositoryMock;
    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private CountryIdValidator countryIdValidatorMock;
    @Mock
    private AccessToken requestAccessToken;
    @Mock
    private PasswordEncoder passwordEncoderMock;

    @InjectMocks
    private UpdateStudentUseCaseImpl updateStudentUseCase;

    @Test
    void updateStudent_ValidRequest_ShouldUpdateStudent() {
        CountryEntity countryEntity = CountryEntity.builder()
                .id(1L)
                .name("Netherlands")
                .code("NL")
                .build();

        StudentEntity studentEntity = StudentEntity.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .country(countryEntity)
                .followedCourses(Collections.emptyList())
                .favoriteCourses(Collections.emptyList())
                .build();

        StudentEntity updatedStudentEntity = StudentEntity.builder()
                .id(studentEntity.getId())
                .firstName("John")
                .lastName("Doe")
                .country(countryEntity)
                .followedCourses(Collections.emptyList())
                .favoriteCourses(Collections.emptyList())
                .build();

        UserEntity userEntity = UserEntity.builder()
                .id(studentEntity.getId())
                .userName("JohnDoe")
                .password("password")
                .student(updatedStudentEntity)
                .build();

        UpdateStudentRequest request = UpdateStudentRequest.builder()
                .id(studentEntity.getId())
                .firstName(updatedStudentEntity.getFirstName())
                .lastName(updatedStudentEntity.getLastName())
                .countryId(countryEntity.getId())
                .userName(updatedStudentEntity.getFirstName() + updatedStudentEntity.getLastName())
                .password("password")
                .build();

        when(requestAccessToken.getStudentId()).thenReturn(studentEntity.getId());
        when(studentRepositoryMock.findById(studentEntity.getId())).thenReturn(Optional.of(studentEntity));
        when(userRepositoryMock.findById(studentEntity.getId())).thenReturn(Optional.of(userEntity));
        doNothing().when(countryIdValidatorMock).validateId(countryEntity.getId());
        when(passwordEncoderMock.encode(request.getPassword())).thenReturn("encodedPassword");
        when(studentRepositoryMock.save(any(StudentEntity.class))).thenReturn(updatedStudentEntity);
        when(userRepositoryMock.save(userEntity)).thenReturn(userEntity);

        updateStudentUseCase.updateStudent(request);

        verify(studentRepositoryMock).findById(studentEntity.getId());
        verify(userRepositoryMock).findById(studentEntity.getId());
        verify(studentRepositoryMock).save(any(StudentEntity.class));
        verify(userRepositoryMock).save(userEntity);
    }

    @Test
    void updateStudent_UnauthorizedAccess_ShouldThrowUnauthorizedDataAccessException() {
        long studentId = 1L;
        long unauthorizedStudentId = 2L;

        UpdateStudentRequest request = UpdateStudentRequest.builder().id(unauthorizedStudentId).build();
        when(requestAccessToken.getStudentId()).thenReturn(studentId);

        assertThrows(UnauthorizedDataAccessException.class, () -> updateStudentUseCase.updateStudent(request));
        verify(studentRepositoryMock, never()).findById(anyLong());
        verify(userRepositoryMock, never()).findById(anyLong());
    }

    @Test
    void updateStudent_InvalidStudentId_ShouldThrowInvalidStudentException() {
        long studentId = 1L;

        UpdateStudentRequest request = UpdateStudentRequest.builder().id(studentId).build();
        when(requestAccessToken.getStudentId()).thenReturn(studentId);
        when(studentRepositoryMock.findById(studentId)).thenReturn(Optional.empty());

        assertThrows(InvalidStudentException.class, () -> updateStudentUseCase.updateStudent(request));
        verify(userRepositoryMock).findById(anyLong());
    }
}