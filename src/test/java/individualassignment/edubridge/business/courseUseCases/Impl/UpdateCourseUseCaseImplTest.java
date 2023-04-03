package individualassignment.edubridge.business.courseUseCases.Impl;

import individualassignment.edubridge.domain.courses.CoursePublishState;
import individualassignment.edubridge.domain.courses.requests.UpdateCourseRequest;
import individualassignment.edubridge.persistence.categories.CategoryRepository;
import individualassignment.edubridge.persistence.categories.entities.CategoryEntity;
import individualassignment.edubridge.persistence.courses.CourseRepository;
import individualassignment.edubridge.persistence.courses.entities.CourseEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateCourseUseCaseImplTest {

    @Mock
    private CourseRepository courseRepositoryMock;

    @Mock
    private CategoryRepository categoryRepositoryMock;

    @InjectMocks
    private UpdateCourseUseCaseImpl updateCourseUseCase;

    @Test
    void updateCourse() {
        CategoryEntity category = CategoryEntity.builder()
                .id(1L)
                .name("category")
                .build();

        CourseEntity actual = CourseEntity.builder()
                .id(1L)
                .title("Java")
                .description("Java Programming")
                .provider("EduBridge")
                .creationDate(LocalDate.parse("2020-01-01"))
                .publishDate(Optional.ofNullable(LocalDate.parse("2020-01-01")))
                .publishState(CoursePublishState.PENDING)
                .category(category)
                .lastModified(null)
                .imageUrl(Optional.of("image"))
                .lessons(Collections.emptyList())
                .build();

        when(courseRepositoryMock.findById(actual.getId())).thenReturn(Optional.of(actual));

        CourseEntity expected = CourseEntity.builder()
                .id(1L)
                .title("Java")
                .description("Java Programming")
                .provider("Sobhi")
                .creationDate(LocalDate.parse("2020-01-01"))
                .publishDate(actual.getPublishState() == CoursePublishState.PUBLISHED ? Optional.of(LocalDate.now()) : Optional.empty())
                .publishState(CoursePublishState.PENDING)
                .category(category)
                .lastModified(null)
                .imageUrl(Optional.of("newImage"))
                .lessons(Collections.emptyList())
                .build();

        when(courseRepositoryMock.saveCourse(expected)).thenReturn(expected);

        UpdateCourseRequest request = UpdateCourseRequest.builder()
                .id(expected.getId())
                .title(expected.getTitle())
                .description(expected.getDescription())
                .provider(expected.getProvider())
                .publishState(expected.getPublishState())
                .categoryId(expected.getCategory().getId())
                .imageUrl(expected.getImageUrl())
                .build();

        when(categoryRepositoryMock.findById(expected.getCategory().getId())).thenReturn(Optional.of(category));

        updateCourseUseCase.updateCourse(request);

        verify(courseRepositoryMock).saveCourse(expected);
        verify(courseRepositoryMock, times(1)).saveCourse(expected);
        verify(courseRepositoryMock).findById(expected.getId());
        verify(categoryRepositoryMock).findById(expected.getCategory().getId());






    }



}