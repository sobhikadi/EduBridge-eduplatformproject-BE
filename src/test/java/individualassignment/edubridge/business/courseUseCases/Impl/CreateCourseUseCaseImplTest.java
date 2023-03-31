package individualassignment.edubridge.business.courseUseCases.Impl;

import individualassignment.edubridge.domain.courses.PublishState;
import individualassignment.edubridge.domain.courses.requests.CreateCourseRequest;
import individualassignment.edubridge.domain.courses.responses.CreateCourseResponse;
import individualassignment.edubridge.persistence.courses.CourseRepository;
import individualassignment.edubridge.persistence.courses.entities.CourseEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateCourseUseCaseImplTest {

    @Mock
    private CourseRepository courseRepositoryMock;

    @InjectMocks
    private CreateCourseUseCaseImpl createCourseUseCase;

    @Test
    void createCourse_ShouldCreateAndSaveCourse_ShouldReturnAddedCourseId() {
        CourseEntity courseEntity = CourseEntity.builder()
                .title("Java")
                .description("Java Programming")
                .provider("EduBridge")
                .creationDate(LocalDate.parse("2020-01-01"))
                .publishDate(Optional.ofNullable(LocalDate.parse("2020-01-01")))
                .publishState(PublishState.PENDING)
                .build();

        CourseEntity expected = CourseEntity.builder()
                .id(1L)
                .title("Java")
                .description("Java Programming")
                .provider("EduBridge")
                .creationDate(LocalDate.parse("2020-01-01"))
                .publishDate(Optional.ofNullable(LocalDate.parse("2020-01-01")))
                .publishState(PublishState.PENDING)
                .build();

        when(courseRepositoryMock.saveCourse(courseEntity)).thenReturn(expected);

        CreateCourseRequest courseRequest = CreateCourseRequest.builder()
                .title("Java")
                .description("Java Programming")
                .provider("EduBridge")
                .creationDate("2020-01-01")
                .publishDate(Optional.of("2020-01-01"))
                .publishState(PublishState.PENDING)
                .build();
        CreateCourseResponse actual = createCourseUseCase.createCourse(courseRequest);

        CreateCourseResponse expectedResponse = CreateCourseResponse.builder()
                .courseId(1L)
                .build();

        assertEquals(expectedResponse, actual);
        verify(courseRepositoryMock).saveCourse(courseEntity);
    }
}