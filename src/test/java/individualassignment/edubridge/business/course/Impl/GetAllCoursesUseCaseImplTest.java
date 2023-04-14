package individualassignment.edubridge.business.course.Impl;

import individualassignment.edubridge.domain.courses.CoursePublishState;
import individualassignment.edubridge.domain.courses.requests.GetAllCoursesRequest;
import individualassignment.edubridge.domain.courses.responses.GetAllCoursesResponse;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllCoursesUseCaseImplTest {

    @Mock
    private CourseRepository courseRepository;
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
                .publishState(CoursePublishState.PUBLISHED)
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
                .publishState(CoursePublishState.PUBLISHED)
                .publishDate(LocalDate.now())
                .lastModified(null)
                .lessons(Collections.emptyList())
                .imageUrl(null)
                .category(category)
                .build();

        when(courseRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(List.of(courseEntity1, courseEntity2));

        GetAllCoursesRequest request = GetAllCoursesRequest.builder().provider(null).build();

        GetAllCoursesResponse response = getAllCoursesUseCase.getAllCourses(request);

        assertEquals(2, response.getCourses().size());

        verify(courseRepository).findAll(Sort.by(Sort.Direction.ASC, "id"));

    }
}