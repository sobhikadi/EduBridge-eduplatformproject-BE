package individualassignment.edubridge.business.course;

import individualassignment.edubridge.domain.courses.requests.UpdateCourseRequest;

public interface UpdateCourseUseCase {
    void updateCourse(UpdateCourseRequest request);
}
