package individualassignment.edubridge.business.users.student.impl;

import individualassignment.edubridge.persistence.users.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteStudentUseCaseImplTest {

    @Mock
    private StudentRepository studentRepositoryMock;
    @InjectMocks
    private DeleteStudentUseCaseImpl deleteStudentUseCase;

    @Test
    void deleteStudent_ValidStudentId_ShouldCallDeleteById() {
       long studentId = 1L;

        doNothing().when(studentRepositoryMock).deleteById(studentId);

        deleteStudentUseCase.deleteStudent(studentId);

        verify(studentRepositoryMock, times(1)).deleteById(studentId);
    }
}
