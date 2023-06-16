package individualassignment.edubridge.business.users.student.impl;

import individualassignment.edubridge.domain.courses.CoursePublishStateEnum;
import individualassignment.edubridge.domain.users.AccessToken;
import individualassignment.edubridge.persistence.address.entities.CountryEntity;
import individualassignment.edubridge.persistence.categories.entities.CategoryEntity;
import individualassignment.edubridge.persistence.courses.CourseRepository;
import individualassignment.edubridge.persistence.courses.entities.CourseEntity;
import individualassignment.edubridge.persistence.users.StudentRepository;
import individualassignment.edubridge.persistence.users.entities.StudentEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteCourseFromFavoritesUseCaseImplTest {

    @Mock
    private StudentRepository studentRepositoryMock;
    @Mock
    private CourseRepository courseRepositoryMock;
    @Mock
    private AccessToken requestAccessToken;
    @InjectMocks
    private DeleteCourseFromFavoritesUseCaseImpl deleteCourseFromFavoritesUseCase;

    @Test
    void deleteCourseFromStudent_ValidRequest_ShouldDeleteCourseFromFavorites() {
        long courseId = 1L;
        long studentId = 1L;

        CourseEntity courseEntity = CourseEntity.builder()
                .id(1L)
                .title("Java")
                .description("Java Programming")
                .provider("EduBridge")
                .creationDate(LocalDate.now())
                .publishState(CoursePublishStateEnum.PENDING)
                .imageUrl(null)
                .category(CategoryEntity.builder().id(1L).name("Programming").build())
                .build();

        StudentEntity savedStudentWithFavCourse = StudentEntity.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .favoriteCourses(new ArrayList<>(List.of(courseEntity)))
                .country(CountryEntity.builder().id(1L).code("NL").name("Netherlands").build())
                .build();

        StudentEntity savedStudent = StudentEntity.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .favoriteCourses(new ArrayList<>())
                .country(CountryEntity.builder().id(1L).code("NL").name("Netherlands").build())
                .build();


        when(studentRepositoryMock.findById(studentId)).thenReturn(Optional.of(savedStudentWithFavCourse));
        when(studentRepositoryMock.save(savedStudent)).thenReturn(savedStudent);

        deleteCourseFromFavoritesUseCase.deleteCourseFromStudent(studentId, courseId);

        verify(studentRepositoryMock).findById(studentId);
        verify(studentRepositoryMock).save(savedStudent);
    }

//    @Test
//    void deleteCourseFromStudent_NonExistentStudent_ShouldThrowException() {
//        DeleteCourseFromStudentRequest request = new DeleteCourseFromStudentRequest(1L, 1L);
//
//        when(requestAccessToken.getStudentId()).thenReturn(1L);
//        when(studentRepositoryMock.findById(request.getStudentId())).thenReturn(Optional.empty());
//
//        assertThrows(InvalidStudentException.class, () -> {
//            deleteCourseFromFavoritesUseCase.deleteCourseFromStudent(request.getStudentId(), request.getCourseId());
//        });
//    }
//
//    @Test
//    void deleteCourseFromStudent_CourseNotInFavorites_ShouldNotThrowException() {
//        DeleteCourseFromStudentRequest request = new DeleteCourseFromStudentRequest(1L, 1L);
//
//        StudentEntity savedStudent = StudentEntity.builder()
//                .id(1L)
//                .firstName("John")
//                .lastName("Doe")
//                .followedCourses(new ArrayList<>())
//                .favoriteCourses(new ArrayList<>())
//                .country(CountryEntity.builder().id(1L).code("NL").name("Netherlands").build())
//                .build();
//
//        CourseEntity courseEntity = CourseEntity.builder()
//                .id(1L)
//                .title("Java")
//                .description("Java Programming")
//                .provider("EduBridge")
//                .creationDate(LocalDate.now())
//                .publishState(CoursePublishStateEnum.PENDING)
//                .imageUrl(null)
//                .category(CategoryEntity.builder().id(1L).name("Programming").build())
//                .build();
//
//        when(requestAccessToken.getStudentId()).thenReturn(1L);
//        when(studentRepositoryMock.findById(request.getStudentId())).thenReturn(Optional.of(savedStudent));
//        when(courseRepositoryMock.findById(request.getCourseId())).thenReturn(Optional.of(courseEntity));
//
//        deleteCourseFromFavoritesUseCase.deleteCourseFromStudent(request.getStudentId(), request.getCourseId());
//
//        verify(studentRepositoryMock).findById(request.getStudentId());
//        verify(courseRepositoryMock).findById(request.getCourseId());
//        verify(studentRepositoryMock).save(savedStudent);
//    }
}