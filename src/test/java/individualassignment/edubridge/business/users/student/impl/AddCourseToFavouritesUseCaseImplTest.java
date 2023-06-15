package individualassignment.edubridge.business.users.student.impl;

import individualassignment.edubridge.business.users.student.exceptions.CourseHasBeenAlredyAddedToFavouritesException;
import individualassignment.edubridge.domain.courses.CoursePublishStateEnum;
import individualassignment.edubridge.domain.users.AccessToken;
import individualassignment.edubridge.domain.users.requests.AddCourseToStudentRequest;
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
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddCourseToFavouritesUseCaseImplTest {

    @Mock
    private StudentRepository studentRepositoryMock;
    @Mock
    private CourseRepository courseRepositoryMock;
    @Mock
    private AccessToken requestAccessToken;
    @InjectMocks
    private AddCourseToFavouritesUseCaseImpl addCourseToFavouritesUseCase;

    @Test
    void addCourseToStudentsFavorites_ValidRequest_ShouldAddCourseToFavorites() {
        AddCourseToStudentRequest request = new AddCourseToStudentRequest(1L, 1L);

        StudentEntity savedStudent = StudentEntity.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .followedCourses(new ArrayList<>())
                .favoriteCourses(new ArrayList<>())
                .country(CountryEntity.builder().id(1L).code("NL").name("Netherlands").build())
                .build();

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
                .followedCourses(new ArrayList<>())
                .favoriteCourses(List.of(courseEntity))
                .country(CountryEntity.builder().id(1L).code("NL").name("Netherlands").build())
                .build();
        when(requestAccessToken.getStudentId()).thenReturn(1L);
        when(studentRepositoryMock.findById(request.getStudentId())).thenReturn(Optional.of(savedStudent));
        when(courseRepositoryMock.findById(request.getCourseId())).thenReturn(Optional.of(courseEntity));
        when(studentRepositoryMock.save(savedStudentWithFavCourse)).thenReturn(savedStudentWithFavCourse);

        addCourseToFavouritesUseCase.addCourseToStudent(request);

        verify(studentRepositoryMock).findById(request.getStudentId());
        verify(courseRepositoryMock).findById(request.getCourseId());
        verify(studentRepositoryMock).save(savedStudentWithFavCourse);
    }

    @Test
    void addCourseToStudentsFavorites_NonExistentCourse_ShouldThrowException() {
        AddCourseToStudentRequest request = new AddCourseToStudentRequest(1L, 1L);

        StudentEntity savedStudent = StudentEntity.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .followedCourses(new ArrayList<>())
                .favoriteCourses(new ArrayList<>())
                .country(CountryEntity.builder().id(1L).code("NL").name("Netherlands").build())
                .build();

        when(requestAccessToken.getStudentId()).thenReturn(1L);
        when(studentRepositoryMock.findById(request.getStudentId())).thenReturn(Optional.of(savedStudent));
        when(courseRepositoryMock.findById(request.getCourseId())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            addCourseToFavouritesUseCase.addCourseToStudent(request);
        });
    }

    @Test
    void addCourseToStudentsFavorites_NonExistentStudent_ShouldThrowException() {
        AddCourseToStudentRequest request = new AddCourseToStudentRequest(1L, 1L);

        when(requestAccessToken.getStudentId()).thenReturn(1L);
        when(studentRepositoryMock.findById(request.getStudentId())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            addCourseToFavouritesUseCase.addCourseToStudent(request);
        });
    }

    @Test
    void addCourseToStudentsFavorites_CourseAlreadyInFavorites_ShouldNotAddCourseAgain() {
        AddCourseToStudentRequest request = new AddCourseToStudentRequest(1L, 1L);

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
                .followedCourses(new ArrayList<>())
                .favoriteCourses(List.of(courseEntity))
                .country(CountryEntity.builder().id(1L).code("NL").name("Netherlands").build())
                .build();

        when(requestAccessToken.getStudentId()).thenReturn(1L);
        when(studentRepositoryMock.findById(request.getStudentId())).thenReturn(Optional.of(savedStudentWithFavCourse));
        when(courseRepositoryMock.findById(request.getCourseId())).thenReturn(Optional.of(courseEntity));


        CourseHasBeenAlredyAddedToFavouritesException exception = assertThrows(
                CourseHasBeenAlredyAddedToFavouritesException.class,
                () -> addCourseToFavouritesUseCase.addCourseToStudent(request)
        );

        assertEquals("400 BAD_REQUEST \"YOU_HAVE_ALREADY_ADDED_THIS_COURSE_TO_YOUR_FAVOURITES\"", exception.getMessage());

        verify(studentRepositoryMock, never()).save(savedStudentWithFavCourse);
    }

}