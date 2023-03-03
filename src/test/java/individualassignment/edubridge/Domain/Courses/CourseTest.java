package individualassignment.edubridge.Domain.Courses;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourseTest {
    @Test
    void toString_ShouldReturnCourseInfoAsString(){
        Course course = createCourseTestRequest();

        String actual = course.toString();

        String expected = "Course(id=1, title=Java For Beginners, description=This course is for beginners in Java and want to start their career in it, nrOfLessons=25, provider=John Doe)";
        assertEquals(expected, actual);

    }

    @Test
    void equals_ShouldReturnTrue_whenHaveSameValues(){
        Course courseOne = createCourseTestRequest();
        Course courseTwo = createCourseTestRequest();

        assertEquals(courseOne, courseTwo);
    }

    @Test
    void equals_ShouldReturnFalse_WhenOneFieldHaveDifferentValue(){
        Course courseOne = createCourseTestRequest();
        Course courseTwo = createCourseTestRequest();
        courseTwo.setNrOfLessons(20);
        assertNotEquals(courseOne, courseTwo);
    }




    private Course createCourseTestRequest(){
        return  Course.builder()
                .id(1L)
                .title("Java For Beginners")
                .description("This course is for beginners in Java and want to start their career in it")
                .nrOfLessons(25)
                .provider("John Doe")
                .build();
    }

}