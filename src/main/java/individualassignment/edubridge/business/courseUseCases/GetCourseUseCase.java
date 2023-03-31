package individualassignment.edubridge.business.courseUseCases;

import individualassignment.edubridge.domain.courses.Course;

import java.util.Optional;

public interface GetCourseUseCase {
    Optional<Course> getCourse(String courseTitle);
}
