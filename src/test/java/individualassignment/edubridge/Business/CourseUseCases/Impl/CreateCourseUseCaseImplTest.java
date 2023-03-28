package individualassignment.edubridge.Business.CourseUseCases.Impl;

import individualassignment.edubridge.Domain.Courses.PublishState;
import individualassignment.edubridge.Domain.Courses.Requests.CreateCourseRequest;
import individualassignment.edubridge.Domain.Courses.Responses.CreateCourseResponse;
import individualassignment.edubridge.Persistence.Courses.CourseRepository;
import individualassignment.edubridge.Persistence.Courses.Entity.CourseEntity;
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