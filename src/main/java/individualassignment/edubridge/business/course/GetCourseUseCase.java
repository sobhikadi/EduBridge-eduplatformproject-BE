package individualassignment.edubridge.business.course;

import individualassignment.edubridge.domain.courses.Course;

import java.util.Optional;

public interface GetCourseUseCase {
    Course getCourse(String courseTitle);
}
