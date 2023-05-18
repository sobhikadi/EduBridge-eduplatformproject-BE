package individualassignment.edubridge.business.course.impl;

import individualassignment.edubridge.business.course.exceptions.InvalidCourseIdException;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteCourseUseCaseImplTest {

    @Mock
    private CourseRepository courseRepositoryMock;

    @Mock
    private UploadImageServiceImpl uploadImageServiceImpl;

    @InjectMocks
    private DeleteCourseUseCaseImpl deleteCourseUseCaseImpl;
    
    @Test
    void deleteCourse_withValidCourseId_deletesCourseAndImage() {
        CategoryEntity category = CategoryEntity.builder()
                .id(1L)
                .name("category")
                .build();
        CourseEntity course = CourseEntity.builder()
                .id(1L)
                .title("Java")
                .description("Java Programming")
                .provider("EduBridge")
                .creationDate(LocalDate.now())
                .publishState(CoursePublishStateEnum.PENDING)
                .imageUrl(null)
                .category(category)
                .build();

        when(courseRepositoryMock.findById(course.getId())).thenReturn(Optional.of(course));

        deleteCourseUseCaseImpl.deleteCourse(course.getId());

        verify(courseRepositoryMock, times(1)).findById(course.getId());
        verify(courseRepositoryMock, times(1)).deleteById(course.getId());
        verify(uploadImageServiceImpl, times(1)).deleteImage(course.getTitle());
    }

    @Test
    void deleteCourse_withInvalidCourseId_throwsInvalidCourseIdException() {
        long courseId = 1L;
        when(courseRepositoryMock.findById(courseId)).thenReturn(Optional.empty());

        assertThrows(InvalidCourseIdException.class, () -> {
            deleteCourseUseCaseImpl.deleteCourse(courseId);
        });

        verify(courseRepositoryMock, times(1)).findById(courseId);
        verify(courseRepositoryMock, never()).deleteById(courseId);
        verify(uploadImageServiceImpl, never()).deleteImage(anyString());

    }
}