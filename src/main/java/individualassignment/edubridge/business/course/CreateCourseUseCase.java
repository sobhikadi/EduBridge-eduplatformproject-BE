package individualassignment.edubridge.business.course;

import individualassignment.edubridge.domain.courses.requests.CreateCourseRequest;
import individualassignment.edubridge.domain.courses.responses.CreateCourseResponse;

public interface CreateCourseUseCase {
    CreateCourseResponse createCourse (CreateCourseRequest request);
}
