package individualassignment.edubridge.business.course.impl;

import individualassignment.edubridge.business.category.exceptions.InvalidCategoryIdException;
import individualassignment.edubridge.business.course.exceptions.CourseNameAlreadyExistsException;
import individualassignment.edubridge.domain.courses.CoursePublishStateEnum;
import individualassignment.edubridge.domain.courses.requests.CreateCourseRequest;
import individualassignment.edubridge.domain.courses.responses.CreateCourseResponse;
import individualassignment.edubridge.persistence.categories.CategoryRepository;
import individualassignment.edubridge.persistence.categories.entities.CategoryEntity;
import individualassignment.edubridge.persistence.courses.CourseRepository;
import individualassignment.edubridge.persistence.courses.entities.CourseEntity;
import individualassignment.edubridge.persistence.lessons.entities.LessonEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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
                .publishState(CoursePublishStateEnum.PENDING)
                .imageUrl(null)
                .category(category)
                .build();

        CourseEntity expected = CourseEntity.builder()
                .id(1L)
                .title("Java")
                .description("Java Programming")
                .provider("EduBridge")
                .creationDate(LocalDate.now())
                .publishState(CoursePublishStateEnum.PENDING)
                .imageUrl(null)
                .category(category)
                .build();

        when(courseRepositoryMock.save(courseEntity)).thenReturn(expected);

        CreateCourseRequest courseRequest = CreateCourseRequest.builder()
                .title("Java")
                .description("Java Programming")
                .provider("EduBridge")
                .publishState(CoursePublishStateEnum.PENDING)
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

    @Test
    void createCourse_WithExistingCourseTitle_ShouldThrowCourseNameAlreadyExistsException() {
        CategoryEntity category = CategoryEntity.builder()
                .id(1L)
                .name("category")
                .build();

        CreateCourseRequest courseRequest = CreateCourseRequest.builder()
                .title("Java")
                .description("Java Programming")
                .provider("EduBridge")
                .publishState(CoursePublishStateEnum.PENDING)
                .image(null)
                .categoryId(category.getId())
                .build();

        when(courseRepositoryMock.existsByTitle(courseRequest.getTitle())).thenReturn(true);

        assertThrows(CourseNameAlreadyExistsException.class,
                () -> createCourseUseCase.createCourse(courseRequest));

        verify(courseRepositoryMock, never()).save(any());
        verify(categoryRepositoryMock, never()).findById(any());
    }

    @Test
    void createCourse_WithInvalidCategoryId_ShouldThrowInvalidCategoryIdException() {
        CreateCourseRequest request = CreateCourseRequest.builder()
                .title("Java")
                .description("Java Programming")
                .provider("EduBridge")
                .publishState(CoursePublishStateEnum.PENDING)
                .image(null)
                .categoryId(1L)
                .build();

        when(courseRepositoryMock.existsByTitle(request.getTitle())).thenReturn(false);
        when(categoryRepositoryMock.findById(request.getCategoryId())).thenReturn(Optional.empty());

        assertThrows(InvalidCategoryIdException.class, () -> createCourseUseCase.createCourse(request));
        verify(courseRepositoryMock, never()).save(any());
    }

    @Test
    void createCourse_WithDescriptionFieldOfNull_ShouldThrowNullPointerException() {
        CreateCourseRequest request = CreateCourseRequest.builder()
                .title("Java")
                .description("")
                .provider("EduBridge")
                .publishState(CoursePublishStateEnum.PENDING)
                .image(null)
                .categoryId(1L)
                .build();

        CategoryEntity category = CategoryEntity.builder().id(1L).name("category").build();

        when(courseRepositoryMock.existsByTitle(request.getTitle())).thenReturn(false);
        doReturn(Optional.of(category)).when(categoryRepositoryMock).findById(1L);

        assertThrows(NullPointerException.class, () -> createCourseUseCase.createCourse(request));
        verify(courseRepositoryMock, times(1)).save(any());

    }

}