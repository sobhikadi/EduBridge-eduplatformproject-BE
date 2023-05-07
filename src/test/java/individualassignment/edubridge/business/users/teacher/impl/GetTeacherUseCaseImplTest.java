package individualassignment.edubridge.business.users.teacher.impl;

import individualassignment.edubridge.business.users.exceptions.UnauthorizedDataAccessException;
import individualassignment.edubridge.domain.users.AccessToken;
import individualassignment.edubridge.domain.users.Teacher;
import individualassignment.edubridge.persistence.address.entities.AddressEntity;
import individualassignment.edubridge.persistence.address.entities.CountryEntity;
import individualassignment.edubridge.persistence.users.TeacherRepository;
import individualassignment.edubridge.persistence.users.entities.TeacherEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetTeacherUseCaseImplTest {

    @Mock
    private TeacherRepository teacherRepositoryMock;

    @Mock
    private AccessToken requestAccessTokenMock;

    @InjectMocks
    private GetTeacherUseCaseImpl getTeacherUseCase;

    @Test
    void getTeacher_ValidTeacherId_ShouldReturnTeacher() {
        long teacherId = 1L;
        TeacherEntity teacherEntity = createTeacherEntity(teacherId, "John", "Doe");

        when(requestAccessTokenMock.getTeacherId()).thenReturn(teacherId);
        when(teacherRepositoryMock.findById(teacherId)).thenReturn(Optional.of(teacherEntity));

        Optional<Teacher> teacherOptional = getTeacherUseCase.getTeacher(teacherId);

        assertTrue(teacherOptional.isPresent());
        assertEquals("John", teacherOptional.get().getFirstName());

        verify(teacherRepositoryMock, times(1)).findById(teacherId);
    }

    @Test
    void getTeacher_InvalidTeacherId_ShouldThrowUnauthorizedDataAccessException() {
        long teacherId = 1L;

        when(requestAccessTokenMock.getTeacherId()).thenReturn(2L);

        assertThrows(UnauthorizedDataAccessException.class, () -> getTeacherUseCase.getTeacher(teacherId));
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
}
