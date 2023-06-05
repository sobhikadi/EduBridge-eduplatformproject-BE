package individualassignment.edubridge.business.course.impl;

import individualassignment.edubridge.domain.courses.CoursePublishStateEnum;
import individualassignment.edubridge.domain.courses.requests.GetAllCoursesRequest;
import individualassignment.edubridge.domain.courses.responses.GetAllCoursesResponse;
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
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllCoursesUseCaseImplTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private GetAllCoursesUseCaseImpl getAllCoursesUseCase;

    @Test
    void getAllCourses_shouldReturnAllCourses() {
        CategoryEntity category = CategoryEntity.builder()
                .id(1L)
                .name("category")
                .build();

        LessonEntity lessonEntity = LessonEntity.builder()
                .id(1L)
                .name("Lesson 1")
                .description("Lesson 1")
                .build();

        CourseEntity courseEntity1 = CourseEntity.builder()
                .id(1L)
                .title("Java")
                .description("Java Programming")
                .provider("EduBridge")
                .creationDate(LocalDate.now())
                .publishState(CoursePublishStateEnum.PUBLISHED)
                .publishDate(LocalDate.now())
                .lastModified(null)
                .lessons(List.of(lessonEntity))
                .imageUrl(null)
                .category(category)
                .build();

        CourseEntity courseEntity2 = CourseEntity.builder()
                .id(2L)
                .title("Python")
                .description("Python Programming")
                .provider("EduBridge")
                .creationDate(LocalDate.now())
                .publishState(CoursePublishStateEnum.PUBLISHED)
                .publishDate(LocalDate.now())
                .lastModified(null)
                .lessons(Collections.emptyList())
                .imageUrl(null)
                .category(category)
                .build();

        when(courseRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(List.of(courseEntity1, courseEntity2));

        GetAllCoursesRequest request = GetAllCoursesRequest.builder().searchTerm(null).build();

        GetAllCoursesResponse response = getAllCoursesUseCase.getAllCourses(request);

        assertEquals(2, response.getCourses().size());

        verify(courseRepository).findAll(Sort.by(Sort.Direction.ASC, "id"));

    }

    @Test
    void testGetAllCoursesWithProviderFilter() {
        GetAllCoursesRequest request = GetAllCoursesRequest.builder().searchTerm("EduBridge").build();

        CourseEntity course = CourseEntity.builder()
                .id(1L)
                .title("Java")
                .description("Java Programming")
                .provider("EduBridge")
                .creationDate(LocalDate.now())
                .publishState(CoursePublishStateEnum.PUBLISHED)
                .publishDate(LocalDate.now())
                .lastModified(null)
                .lessons(Collections.emptyList())
                .imageUrl(null)
                .category(CategoryEntity.builder().id(1L).name("Category A").build())
                .build();

        when(courseRepository.findAllByProviderContainingIgnoreCase("EduBridge")).thenReturn(List.of(course));

        GetAllCoursesResponse response = getAllCoursesUseCase.getAllCourses(request);

        assertEquals(1, response.getCourses().size());
        assertEquals("Java", response.getCourses().get(0).getTitle());
        verify(courseRepository, times(1)).findAllByProviderContainingIgnoreCase("EduBridge");
    }

    @Test
    void testGetAllCoursesWithCategoryFilter() {
        GetAllCoursesRequest request = GetAllCoursesRequest.builder().categoryId(1L).build();

        CategoryEntity category = CategoryEntity.builder().id(1L).name("Category A").build();

        CourseEntity course = CourseEntity.builder()
                .id(1L)
                .title("Java")
                .description("Java Programming")
                .provider("EduBridge")
                .creationDate(LocalDate.now())
                .publishState(CoursePublishStateEnum.PUBLISHED)
                .publishDate(LocalDate.now())
                .lastModified(null)
                .lessons(Collections.emptyList())
                .imageUrl(null)
                .category(category)
                .build();

        when(categoryRepository.findById(category.getId()))
                .thenReturn(java.util.Optional.of(category));
        when(courseRepository.findAllByCategoryOrderById(category)).thenReturn(List.of(course));

        GetAllCoursesResponse response = getAllCoursesUseCase.getAllCourses(request);

        assertEquals(1, response.getCourses().size());
        assertEquals("Java", response.getCourses().get(0).getTitle());
        verify(courseRepository, times(1)).findAllByCategoryOrderById(category);
    }

    @Test
    void testGetAllCategories_shouldReturnEmptyListWhenNoCategoriesFound() {
        when(courseRepository.findAll(any(Sort.class))).thenReturn(List.of());

        GetAllCoursesResponse response = getAllCoursesUseCase.getAllCourses(GetAllCoursesRequest.builder().build());

        assertTrue(response.getCourses().isEmpty());
        verify(courseRepository, times(1)).findAll(any(Sort.class));
    }
}