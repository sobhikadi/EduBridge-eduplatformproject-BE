package individualassignment.edubridge.business.course.impl;

import individualassignment.edubridge.domain.courses.Course;
import individualassignment.edubridge.domain.courses.CoursePublishStateEnum;
import individualassignment.edubridge.persistence.categories.entities.CategoryEntity;
import individualassignment.edubridge.persistence.courses.CourseRepository;
import individualassignment.edubridge.persistence.courses.entities.CourseEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetCourseUseCaseImplTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private GetCourseUseCaseImpl getCourseUseCase;

    @Test
    void testGetCourse_whenCourseFound() {

        CourseEntity courseEntity = CourseEntity.builder()
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

        when(courseRepository.findByTitleContainingIgnoreCase(courseEntity.getTitle()))
                .thenReturn(Optional.of(courseEntity));

        Course result = getCourseUseCase.getCourse(courseEntity.getTitle());

        assertEquals(Optional.of(CourseConverter.convert(courseEntity)), Optional.ofNullable(result));
        verify(courseRepository, times(1)).findByTitleContainingIgnoreCase(courseEntity.getTitle());
    }

    @Test
    void testGetCourse_whenCourseNotFound() {
        when(courseRepository.findByTitleContainingIgnoreCase("Non-Existent Course"))
                .thenReturn(Optional.empty());

        Course result = getCourseUseCase.getCourse("Non-Existent Course");

        assertEquals(Optional.empty(), Optional.ofNullable(result));
        verify(courseRepository).findByTitleContainingIgnoreCase("Non-Existent Course");
    }
}