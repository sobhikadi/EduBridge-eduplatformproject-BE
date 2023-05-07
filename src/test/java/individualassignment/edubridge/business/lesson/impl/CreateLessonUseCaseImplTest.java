package individualassignment.edubridge.business.lesson.impl;

import individualassignment.edubridge.business.course.exceptions.InvalidCourseIdException;
import individualassignment.edubridge.business.lesson.exceptions.LessonNameAlreadyExistsException;
import individualassignment.edubridge.domain.lessons.requests.CreateLessonRequest;
import individualassignment.edubridge.domain.lessons.responses.CreateLessonResponse;
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
class CreateLessonUseCaseImplTest {

    @Mock
    private LessonRepository lessonRepositoryMock;
    @Mock
    private CourseRepository courseRepositoryMock;
    @InjectMocks
    private CreateLessonUseCaseImpl createLessonUseCase;

    @Test
    void createLesson_ShouldCreateAndSaveLesson() {
        CourseEntity course = CourseEntity.builder()
                .id(1L)
                .title("Java")
                .build();

        LessonEntity lessonEntity = LessonEntity.builder()
                .name("Lesson 1")
                .description("Lesson 1")
                .course(course)
                .build();

        LessonEntity expected = LessonEntity.builder()
                .id(1L)
                .name("Lesson 1")
                .description("Lesson 1")
                .course(course)
                .build();

        when(lessonRepositoryMock.save(lessonEntity)).thenReturn(expected);

        CreateLessonRequest lessonRequest = CreateLessonRequest.builder()
                .name("Lesson 1")
                .description("Lesson 1")
                .courseId(course.getId())
                .build();

        when(lessonRepositoryMock.existsByName(lessonRequest.getName())).thenReturn(false);

        when(courseRepositoryMock.findById(course.getId())).thenReturn(Optional.of(course));

        CreateLessonResponse actual = createLessonUseCase.createLesson(lessonRequest);

        CreateLessonResponse expectedResponse = CreateLessonResponse.builder()
                .lessonId(1L)
                .build();

        assertEquals(expectedResponse, actual);
        verify(lessonRepositoryMock).save(lessonEntity);
        verify(lessonRepositoryMock).existsByName(lessonRequest.getName());
        verify(courseRepositoryMock).findById(course.getId());
    }

    @Test
    void createLesson_WithExistingLessonName_ShouldThrowLessonNameAlreadyExistsException() {
        CourseEntity course = CourseEntity.builder()
                .id(1L)
                .title("Java")
                .build();

        CreateLessonRequest lessonRequest = CreateLessonRequest.builder()
                .name("Lesson 1")
                .description("Lesson 1")
                .courseId(course.getId())
                .build();

        when(lessonRepositoryMock.existsByName(lessonRequest.getName())).thenReturn(true);

        assertThrows(LessonNameAlreadyExistsException.class,
                () -> createLessonUseCase.createLesson(lessonRequest));

        verify(lessonRepositoryMock, never()).save(any());
        verify(courseRepositoryMock, never()).findById(any());
    }

    @Test
    void createLesson_WithInvalidCourseId_ShouldThrowInvalidCourseIdException() {
        CreateLessonRequest request = CreateLessonRequest.builder()
                .name("Lesson 1")
                .description("Lesson 1")
                .courseId(1L)
                .build();

        when(lessonRepositoryMock.existsByName(request.getName())).thenReturn(false);
        when(courseRepositoryMock.findById(request.getCourseId())).thenReturn(Optional.empty());

        assertThrows(InvalidCourseIdException.class, () -> createLessonUseCase.createLesson(request));
        verify(lessonRepositoryMock, never()).save(any());
    }
}
