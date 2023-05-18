package individualassignment.edubridge.business.lesson.impl;

import individualassignment.edubridge.business.course.exceptions.InvalidCourseIdException;
import individualassignment.edubridge.business.lesson.exceptions.InvalidLessonIdException;
import individualassignment.edubridge.domain.lessons.requests.UpdateLessonRequest;
import individualassignment.edubridge.persistence.courses.CourseRepository;
import individualassignment.edubridge.persistence.courses.entities.CourseEntity;
import individualassignment.edubridge.persistence.lessons.LessonRepository;
import individualassignment.edubridge.persistence.lessons.entities.LessonEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateLessonUseCaseImplTest {

    @Mock
    private LessonRepository lessonRepositoryMock;
    @Mock
    private CourseRepository courseRepositoryMock;
    @InjectMocks
    private UpdateLessonUseCaseImpl updateLessonUseCase;

    @Test
    void updateLesson_ValidLessonId_ShouldUpdateLesson() {
        LessonEntity existingLesson = LessonEntity.builder()
                .id(1L)
                .name("Lesson 1")
                .description("Lesson 1 Description")
                .build();

        CourseEntity course = CourseEntity.builder()
                .id(1L)
                .title("Java")
                .description("Java Programming")
                .build();

        LessonEntity updatedLesson = LessonEntity.builder()
                .id(existingLesson.getId())
                .name("Updated Lesson 1")
                .description("Updated Lesson 1 Description")
                .course(course)
                .build();

        when(lessonRepositoryMock.findById(existingLesson.getId())).thenReturn(Optional.of(existingLesson));
        when(courseRepositoryMock.findById(course.getId())).thenReturn(Optional.of(course));

        UpdateLessonRequest request = UpdateLessonRequest.builder()
                .id(existingLesson.getId())
                .name(updatedLesson.getName())
                .description(updatedLesson.getDescription())
                .courseId(course.getId())
                .build();

        updateLessonUseCase.updateLesson(request);


        assertEquals(request.getName(), updatedLesson.getName());
        assertEquals(request.getDescription(), updatedLesson.getDescription());
        assertEquals(course, updatedLesson.getCourse());

        verify(lessonRepositoryMock).save(updatedLesson);
        verify(courseRepositoryMock).findById(course.getId());
        verify(lessonRepositoryMock).findById(existingLesson.getId());
    }

    @Test
    void updateLesson_InvalidLessonId_ShouldThrowInvalidLessonIdException() {
        UpdateLessonRequest request = UpdateLessonRequest.builder()
                .id(1L)
                .name("Updated Lesson 1")
                .description("Updated Lesson 1 Description")
                .courseId(1L)
                .build();

        when(lessonRepositoryMock.findById(request.getId())).thenReturn(Optional.empty());

        assertThrows(InvalidLessonIdException.class, () -> updateLessonUseCase.updateLesson(request));
        verify(lessonRepositoryMock, never()).save(any());
    }

    @Test
    void updateLesson_InvalidCourseId_ShouldThrowInvalidCourseIdException() {
        // Arrange
        LessonEntity existingLesson = LessonEntity.builder()
                .id(1L)
                .name("Lesson 1")
                .description("Lesson 1 Description")
                .build();

        when(lessonRepositoryMock.findById(existingLesson.getId())).thenReturn(Optional.of(existingLesson));

        UpdateLessonRequest request = UpdateLessonRequest.builder()
                .id(existingLesson.getId())
                .name("Updated Lesson 1")
                .description("Updated Lesson 1 Description")
                .courseId(1L)
                .build();

        when(courseRepositoryMock.findById(request.getCourseId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(InvalidCourseIdException.class, () -> updateLessonUseCase.updateLesson(request));
        verify(lessonRepositoryMock, never()).save(any());
    }
}
