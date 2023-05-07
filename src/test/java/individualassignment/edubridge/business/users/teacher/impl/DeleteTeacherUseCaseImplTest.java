package individualassignment.edubridge.business.users.teacher.impl;

import individualassignment.edubridge.persistence.users.TeacherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteTeacherUseCaseImplTest {

    @Mock
    private TeacherRepository teacherRepositoryMock;

    @InjectMocks
    private DeleteTeacherUseCaseImpl deleteTeacherUseCase;

    @Test
    void deleteTeacher_ValidTeacherId_ShouldDeleteTeacher() {
        Long teacherId = 1L;

        doNothing().when(teacherRepositoryMock).deleteById(teacherId);

        deleteTeacherUseCase.deleteTeacher(teacherId);

        verify(teacherRepositoryMock).deleteById(teacherId);
    }
}
