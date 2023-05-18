package individualassignment.edubridge.business.lesson.impl;

import individualassignment.edubridge.persistence.lessons.LessonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteLessonUseCaseImplTest {

    @Mock
    private LessonRepository lessonRepositoryMock;
    @InjectMocks
    private DeleteLessonUseCaseImpl deleteLessonUseCase;

    @Test
    void deleteLesson_ShouldDeleteLessonById() {
        long lessonId = 1L;

        deleteLessonUseCase.deleteLesson(lessonId, 0L);

        verify(lessonRepositoryMock).deleteById(lessonId);
        verify(lessonRepositoryMock, never()).deleteAllByCourseId(anyLong());
    }

    @Test
    void deleteLesson_ShouldDeleteLessonsByCourseId() {
        long courseId = 1L;

        deleteLessonUseCase.deleteLesson(0L, courseId);

        verify(lessonRepositoryMock, never()).deleteById(anyLong());
        verify(lessonRepositoryMock).deleteAllByCourseId(courseId);
    }

    @Test
    void deleteLesson_WithBothLessonIdAndCourseIdAsZero_ShouldNotCallDeleteMethods() {
        deleteLessonUseCase.deleteLesson(0L, 0L);

        verify(lessonRepositoryMock, never()).deleteById(anyLong());
        verify(lessonRepositoryMock, never()).deleteAllByCourseId(anyLong());
    }
}