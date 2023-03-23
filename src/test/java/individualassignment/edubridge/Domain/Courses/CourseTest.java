package individualassignment.edubridge.Domain.Courses;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CourseTest {

    @Test
    void toString_ShouldReturnCourseInfoAsString(){
        Course course = createCourseTestRequest();

        String actual = course.toString();

        String expected = String.format("Course(id=1, title=Java For Beginners, description=This course is for beginners in Java and want to start their career in it, provider=John Doe, creationDate=%s, publishDate=Optional[%s], lastModified=null)", LocalDate.now(), LocalDate.now());
        assertEquals(expected, actual);

    }

    @Test
    void equals_ShouldReturnTrue_whenHaveSameValues(){
        Course courseOne = createCourseTestRequest();
        Course courseTwo = Course.builder()
                .id(1L)
                .title("Java For Beginners")
                .description("This course is for beginners in Java and want to start their career in it")
                .provider("John Doe")
                .creationDate(LocalDate.now())
                .publishDate(Optional.ofNullable(LocalDate.now()))
                .build();

        assertEquals(courseOne, courseTwo);
    }

    @Test
    void equals_ShouldReturnFalse_WhenOneFieldHaveDifferentValue(){
        Course courseOne = Course.builder()
                .id(1L)
                .title("Beginners")
                .description("This course is for beginners in Java and want to start their career in it")
                .provider("John Doe")
                .creationDate(LocalDate.now())
                .publishDate(Optional.ofNullable(LocalDate.now()))
                .build();
        Course courseTwo = createCourseTestRequest();
        assertNotEquals(courseOne, courseTwo);
    }




    private Course createCourseTestRequest(){
        return  Course.builder()
                .id(1L)
                .title("Java For Beginners")
                .description("This course is for beginners in Java and want to start their career in it")
                .provider("John Doe")
                .creationDate(LocalDate.now())
                .publishDate(Optional.ofNullable(LocalDate.now()))
                .build();
    }

}