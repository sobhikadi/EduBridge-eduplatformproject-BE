package individualassignment.edubridge.Business.CourseUseCases;

import individualassignment.edubridge.Domain.Courses.Course;

import java.util.Optional;

public interface GetCourseUseCase {
    Optional<Course> getCourse(String courseTitle);
}
