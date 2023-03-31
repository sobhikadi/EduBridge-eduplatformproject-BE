package individualassignment.edubridge.business.courseUseCases;

import individualassignment.edubridge.domain.courses.requests.CreateCourseRequest;
import individualassignment.edubridge.domain.courses.responses.CreateCourseResponse;

public interface CreateCourseUseCase {
    CreateCourseResponse createCourse (CreateCourseRequest request);
}
