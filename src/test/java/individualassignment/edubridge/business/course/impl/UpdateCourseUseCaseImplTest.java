package individualassignment.edubridge.business.course.impl;

import individualassignment.edubridge.domain.courses.CoursePublishStateEnum;
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
import java.time.format.DateTimeFormatter;
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

        CourseEntity oldCourse = CourseEntity.builder()
                .id(1L)
                .title("Java")
                .description("Java Programming")
                .provider("EduBridge")
                .creationDate(LocalDate.now())
                .publishState(CoursePublishStateEnum.PENDING)
                .category(category)
                .lastModified(null)
                .imageUrl(null)
                .lessons(Collections.emptyList())
                .build();

        when(courseRepositoryMock.findById(oldCourse.getId())).thenReturn(Optional.of(oldCourse));

        CourseEntity newCourse = CourseEntity.builder()
                .id(1L)
                .title("Java")
                .description("Java Programming")
                .provider("Sobhi")
                .creationDate(LocalDate.now())
                .publishDate(oldCourse.getPublishState() == CoursePublishStateEnum.PUBLISHED ? LocalDate.now() : null)
                .publishState(CoursePublishStateEnum.PENDING)
                .category(category)
                .lastModified(LocalDateTime.parse(LocalDateTime
                        .now()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .imageUrl(null)
                .lessons(Collections.emptyList())
                .build();

        when(courseRepositoryMock.save(newCourse)).thenReturn(newCourse);

        UpdateCourseRequest request = UpdateCourseRequest.builder()
                .id(newCourse.getId())
                .title(newCourse.getTitle())
                .description(newCourse.getDescription())
                .provider(newCourse.getProvider())
                .publishState(newCourse.getPublishState())
                .categoryId(newCourse.getCategory().getId())
                .image(null)
                .build();

        when(categoryRepositoryMock.findById(newCourse.getCategory().getId())).thenReturn(Optional.of(category));

        updateCourseUseCase.updateCourse(request);

        verify(courseRepositoryMock).save(newCourse);
        verify(courseRepositoryMock, times(1)).save(newCourse);
        verify(courseRepositoryMock).findById(newCourse.getId());
        verify(categoryRepositoryMock).findById(newCourse.getCategory().getId());






    }



}