package individualassignment.edubridge.business.courseUseCases.Impl;

import individualassignment.edubridge.domain.courses.CoursePublishState;
import individualassignment.edubridge.domain.courses.requests.CreateCourseRequest;
import individualassignment.edubridge.domain.courses.responses.CreateCourseResponse;
import individualassignment.edubridge.persistence.categories.CategoryRepository;
import individualassignment.edubridge.persistence.courses.CourseRepository;
import individualassignment.edubridge.persistence.categories.entities.CategoryEntity;
import individualassignment.edubridge.persistence.courses.entities.CourseEntity;
import individualassignment.edubridge.persistence.lessons.entities.LessonEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateCourseUseCaseImplTest {

    @Mock
    private CourseRepository courseRepositoryMock;

    @Mock
    private CategoryRepository categoryRepositoryMock;

    @InjectMocks
    private CreateCourseUseCaseImpl createCourseUseCase;

    @Test
    void createCourse_ShouldCreateAndSaveCourse() {
        CategoryEntity category = CategoryEntity.builder()
                .id(1L)
                .name("category")
                .build();

        LessonEntity lessonEntity = LessonEntity.builder()
                .id(1L)
                .name("Lesson 1")
                .description("Lesson 1")
                .build();

        CourseEntity courseEntity = CourseEntity.builder()
                .title("Java")
                .description("Java Programming")
                .provider("EduBridge")
                .creationDate(LocalDate.now())
                .publishState(CoursePublishState.PENDING)
                .imageUrl(null)
                .category(category)
                .build();

        CourseEntity expected = CourseEntity.builder()
                .id(1L)
                .title("Java")
                .description("Java Programming")
                .provider("EduBridge")
                .creationDate(LocalDate.now())
                .publishState(CoursePublishState.PENDING)
                .imageUrl(null)
                .category(category)
                .build();

        when(courseRepositoryMock.save(courseEntity)).thenReturn(expected);

        CreateCourseRequest courseRequest = CreateCourseRequest.builder()
                .title("Java")
                .description("Java Programming")
                .provider("EduBridge")
                .publishState(CoursePublishState.PENDING)
                .image(null)
                .categoryId(category.getId())
                .build();

        when(courseRepositoryMock.existsByTitle(courseRequest.getTitle())).thenReturn(false);

        when(categoryRepositoryMock.findById(category.getId())).thenReturn(Optional.of(category));

        CreateCourseResponse actual = createCourseUseCase.createCourse(courseRequest);

        CreateCourseResponse expectedResponse = CreateCourseResponse.builder()
                .courseId(1L)
                .build();

        assertEquals(expectedResponse, actual);
        verify(courseRepositoryMock).save(courseEntity);
        verify(courseRepositoryMock).existsByTitle(courseRequest.getTitle());
        verify(categoryRepositoryMock).findById(category.getId());
    }
}