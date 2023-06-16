package individualassignment.edubridge.business.users.student.impl;

import individualassignment.edubridge.domain.users.requests.GetAllStudentsRequest;
import individualassignment.edubridge.domain.users.responses.GetAllStudentsResponse;
import individualassignment.edubridge.persistence.address.entities.CountryEntity;
import individualassignment.edubridge.persistence.users.StudentRepository;
import individualassignment.edubridge.persistence.users.entities.StudentEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllStudentsUseCaseImplTest {

    @Mock
    private StudentRepository studentRepositoryMock;
    @InjectMocks
    private GetAllStudentsUseCaseImpl getAllStudentsUseCase;

    @Test
    void getAllStudents_WithoutCountryCode_ShouldReturnAllStudents() {
        GetAllStudentsRequest request = new GetAllStudentsRequest();

        CountryEntity country = CountryEntity.builder()
                .code("US")
                .name("United States")
                .build();

        StudentEntity student1 = StudentEntity.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .country(country)
                .favoriteCourses(Collections.emptyList())
                .build();
        StudentEntity student2 = StudentEntity.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Doe")
                .country(country)
                .favoriteCourses(Collections.emptyList())
                .build();
        List<StudentEntity> studentEntities = List.of(student1, student2);

        when(studentRepositoryMock.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(studentEntities);

        GetAllStudentsResponse response = getAllStudentsUseCase.getAllStudents(request);

        assertEquals(2, response.getStudents().size());

        verify(studentRepositoryMock,
                times(1)).findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Test
    void getAllStudents_WithCountryCode_ShouldReturnStudentsByCountryCode() {
        GetAllStudentsRequest request = GetAllStudentsRequest.builder()
                .countryCode("US")
                .build();

        CountryEntity country = CountryEntity.builder()
                .code("US")
                .name("United States")
                .build();

        StudentEntity student1 = StudentEntity.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .country(country)
                .favoriteCourses(Collections.emptyList())
                .build();
        StudentEntity student2 = StudentEntity.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Doe")
                .country(country)
                .favoriteCourses(Collections.emptyList())
                .build();
        List<StudentEntity> studentEntities = List.of(student1, student2);

        when(studentRepositoryMock.findAllByCountryCodeOrderByIdAsc(request.getCountryCode())).thenReturn(studentEntities);

        GetAllStudentsResponse response = getAllStudentsUseCase.getAllStudents(request);

        assertEquals(2, response.getStudents().size());
        verify(studentRepositoryMock,
                times(1)).findAllByCountryCodeOrderByIdAsc(request.getCountryCode());
    }
}
