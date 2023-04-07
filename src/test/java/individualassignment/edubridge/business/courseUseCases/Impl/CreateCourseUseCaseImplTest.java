//package individualassignment.edubridge.business.courseUseCases.Impl;
//
//import individualassignment.edubridge.domain.courses.CoursePublishState;
//import individualassignment.edubridge.domain.courses.requests.CreateCourseRequest;
//import individualassignment.edubridge.domain.courses.responses.CreateCourseResponse;
//import individualassignment.edubridge.persistence.categories.CategoryRepository;
//import individualassignment.edubridge.persistence.courses.CourseRepository;
//import individualassignment.edubridge.persistence.categories.entities.CategoryEntity;
//import individualassignment.edubridge.persistence.courses.entities.CourseEntity;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.time.LocalDate;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class CreateCourseUseCaseImplTest {
//
//    @Mock
//    private CourseRepository courseRepositoryMock;
//
//    @Mock
//    private CategoryRepository categoryRepositoryMock;
//
//    @InjectMocks
//    private CreateCourseUseCaseImpl createCourseUseCase;
//
//    @Test
//    void createCourse_ShouldCreateAndSaveCourse_ShouldReturnAddedCourseId() {
//        CategoryEntity category = CategoryEntity.builder()
//                .id(1L)
//                .name("category")
//                .build();
//
//        CourseEntity courseEntity = CourseEntity.builder()
//                .title("Java")
//                .description("Java Programming")
//                .provider("EduBridge")
//                .creationDate(LocalDate.now())
//                .publishDate(null)
//                .publishState(CoursePublishState.PENDING)
//                .imageUrl(Optional.of("url"))
//                .category(category)
//                .build();
//
//        CourseEntity expected = CourseEntity.builder()
//                .id(1L)
//                .title("Java")
//                .description("Java Programming")
//                .provider("EduBridge")
//                .creationDate(LocalDate.parse("2020-01-01"))
//                .publishDate(null)
//                .publishState(CoursePublishState.PENDING)
//                .imageUrl(Optional.of("url"))
//                .category(category)
//                .build();
//
//        when(courseRepositoryMock.saveCourse(courseEntity)).thenReturn(expected);
//
//        CreateCourseRequest courseRequest = CreateCourseRequest.builder()
//                .title("Java")
//                .description("Java Programming")
//                .provider("EduBridge")
//                .publishState(CoursePublishState.PENDING)
//                .image(null)
//                .categoryId(category.getId())
//                .build();
//
//        when(courseRepositoryMock.existsByName(courseRequest.getTitle())).thenReturn(false);
//
//        when(categoryRepositoryMock.findById(category.getId())).thenReturn(Optional.of(category));
//
//        CreateCourseResponse actual = createCourseUseCase.createCourse(courseRequest);
//
//        CreateCourseResponse expectedResponse = CreateCourseResponse.builder()
//                .courseId(1L)
//                .build();
//
//        assertEquals(expectedResponse, actual);
//        verify(courseRepositoryMock).saveCourse(courseEntity);
//        verify(courseRepositoryMock).existsByName(courseRequest.getTitle());
//        verify(categoryRepositoryMock).findById(category.getId());
//    }
//}