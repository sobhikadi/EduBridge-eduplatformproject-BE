package individualassignment.edubridge.business.lesson.impl;

import individualassignment.edubridge.domain.lessons.Lesson;
import individualassignment.edubridge.persistence.lessons.LessonRepository;
import individualassignment.edubridge.persistence.lessons.entities.LessonEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetLessonUseCaseImplTest {

    @Mock
    private LessonRepository lessonRepositoryMock;
    @InjectMocks
    private GetLessonUseCaseImpl getLessonUseCase;

    @Test
    void getLesson_WithLessonTitle_ShouldReturnLesson() {
        String lessonTitle = "Lesson 1";
        LessonEntity lessonEntity = LessonEntity.builder()
                .id(1L)
                .name(lessonTitle)
                .description("Description 1")
                .build();

        when(lessonRepositoryMock.findByName(lessonTitle)).thenReturn(Optional.of(lessonEntity));

        Optional<Lesson> actual = getLessonUseCase.getLesson(lessonTitle, null);

        Lesson expectedLesson = LessonConverter.convert(lessonEntity);
        Optional<Lesson> expected = Optional.of(expectedLesson);

        assertEquals(expected, actual);
        verify(lessonRepositoryMock).findByName(lessonTitle);
        verify(lessonRepositoryMock, never()).findByCourseId(anyLong());
    }

    @Test
    void getLesson_WithCourseId_ShouldReturnLesson() {
        Long courseId = 1L;
        LessonEntity lessonEntity = LessonEntity.builder()
                .id(1L)
                .name("Lesson 1")
                .description("Description 1")
                .build();

        when(lessonRepositoryMock.findByCourseId(courseId)).thenReturn(Optional.of(lessonEntity));

        Optional<Lesson> actual = getLessonUseCase.getLesson(null, courseId);

        Lesson expectedLesson = LessonConverter.convert(lessonEntity);
        Optional<Lesson> expected = Optional.of(expectedLesson);

        assertEquals(expected, actual);
        verify(lessonRepositoryMock, never()).findByName(anyString());
        verify(lessonRepositoryMock).findByCourseId(courseId);
    }

    @Test
    void getLesson_WithNoParameters_ShouldReturnEmptyOptional() {
        Optional<Lesson> actual = getLessonUseCase.getLesson(null, null);

        Optional<Lesson> expected = Optional.empty();

        assertEquals(expected, actual);
        verify(lessonRepositoryMock, never()).findByName(anyString());
        verify(lessonRepositoryMock, never()).findByCourseId(anyLong());
    }
}