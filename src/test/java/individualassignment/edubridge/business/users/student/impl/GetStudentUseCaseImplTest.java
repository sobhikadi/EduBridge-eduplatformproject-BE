package individualassignment.edubridge.business.users.student.impl;

import individualassignment.edubridge.business.users.exceptions.UnauthorizedDataAccessException;
import individualassignment.edubridge.domain.users.AccessToken;
import individualassignment.edubridge.domain.users.Student;
import individualassignment.edubridge.persistence.address.entities.CountryEntity;
import individualassignment.edubridge.persistence.users.StudentRepository;
import individualassignment.edubridge.persistence.users.entities.StudentEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetStudentUseCaseImplTest {

    @Mock
    private StudentRepository studentRepositoryMock;
    @InjectMocks
    private GetStudentUseCaseImpl getStudentUseCase;
    @Mock
    private AccessToken requestAccessToken;

    @Test
    void getStudent_ValidStudentId_ShouldReturnStudent() {
        // Arrange
        long studentId = 1L;

        CountryEntity countryEntity = CountryEntity.builder()
                .id(1L)
                .name("Netherlands")
                .build();

        StudentEntity studentEntity = StudentEntity.builder()
                .id(studentId)
                .firstName("John")
                .lastName("Doe")
                .country(countryEntity)
                .favoriteCourses(Collections.emptyList())
                .followedCourses(Collections.emptyList())
                .build();

        when(requestAccessToken.getStudentId()).thenReturn(studentId);
        when(studentRepositoryMock.findById(studentId)).thenReturn(Optional.of(studentEntity));

        Optional<Student> studentOptional = getStudentUseCase.getStudent(studentId);

        assertTrue(studentOptional.isPresent());
        Student student = studentOptional.get();
        assertEquals(studentEntity.getId(), student.getId());

        verify(studentRepositoryMock, times(1)).findById(studentId);
    }

    @Test
    void getStudent_InvalidStudentId_ShouldReturnEmptyOptional() {
        long studentId = 1L;
        when(requestAccessToken.getStudentId()).thenReturn(studentId);
        when(studentRepositoryMock.findById(studentId)).thenReturn(Optional.empty());

        Optional<Student> studentOptional = getStudentUseCase.getStudent(studentId);

        assertTrue(studentOptional.isEmpty());
        verify(studentRepositoryMock, times(1)).findById(studentId);
    }

    @Test
    void getStudent_UnauthorizedAccess_ShouldThrowUnauthorizedDataAccessException() {
        long studentId = 1L;
        long unauthorizedStudentId = 2L;

        when(requestAccessToken.getStudentId()).thenReturn(studentId);

        assertThrows(UnauthorizedDataAccessException.class, () -> getStudentUseCase.getStudent(unauthorizedStudentId));
    }
}
