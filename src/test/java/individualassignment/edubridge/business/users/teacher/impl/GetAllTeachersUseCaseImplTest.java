package individualassignment.edubridge.business.users.teacher.impl;

import individualassignment.edubridge.domain.users.requests.GetAllTeachersRequest;
import individualassignment.edubridge.domain.users.responses.GetAllTeachersResponse;
import individualassignment.edubridge.persistence.address.entities.AddressEntity;
import individualassignment.edubridge.persistence.address.entities.CountryEntity;
import individualassignment.edubridge.persistence.users.TeacherRepository;
import individualassignment.edubridge.persistence.users.entities.TeacherEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllTeachersUseCaseImplTest {

    @Mock
    private TeacherRepository teacherRepositoryMock;

    @InjectMocks
    private GetAllTeachersUseCaseImpl getAllTeachersUseCase;

    @Test
    void getAllTeachers_WithoutCountryCode_ShouldReturnAllTeachers() {
        GetAllTeachersRequest request = new GetAllTeachersRequest();
        List<TeacherEntity> teacherEntities = List.of(
                createTeacherEntity(1L, "John", "Doe"),
                createTeacherEntity(2L, "Jane", "Smith")
        );

        when(teacherRepositoryMock.findAll(Sort.by(Sort.Direction.ASC, "id")))
                .thenReturn(teacherEntities);

        GetAllTeachersResponse response = getAllTeachersUseCase.getAllTeachers(request);

        assertEquals(2, response.getTeachers().size());
        verify(teacherRepositoryMock, times(1))
                .findAll(Sort.by(Sort.Direction.ASC, "id"));
        verify(teacherRepositoryMock, never())
                .findAllByAddress_CountryCodeOrderByIdAsc(anyString());

    }

    @Test
    void getAllTeachers_WithCountryCode_ShouldReturnFilteredTeachers() {
        GetAllTeachersRequest request = new GetAllTeachersRequest();
        request.setCountryCode("US");
        List<TeacherEntity> teacherEntities = List.of(
                createTeacherEntity(1L, "John", "Doe")
        );

        when(teacherRepositoryMock.findAllByAddress_CountryCodeOrderByIdAsc("US")).thenReturn(teacherEntities);

        GetAllTeachersResponse response = getAllTeachersUseCase.getAllTeachers(request);

        assertEquals(1, response.getTeachers().size());
          verify(teacherRepositoryMock, never())
                 .findAll(Sort.by(Sort.Direction.ASC, "id"));
        verify(teacherRepositoryMock, times(1)).findAllByAddress_CountryCodeOrderByIdAsc("US");
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
