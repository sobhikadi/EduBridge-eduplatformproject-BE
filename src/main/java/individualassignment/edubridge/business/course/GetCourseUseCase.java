package individualassignment.edubridge.business.course;

import individualassignment.edubridge.domain.courses.Course;

public interface GetCourseUseCase {
    Course getCourse(String courseTitle);
}
