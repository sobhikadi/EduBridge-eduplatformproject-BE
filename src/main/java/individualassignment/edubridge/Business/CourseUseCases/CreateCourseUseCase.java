package individualassignment.edubridge.Business.CourseUseCases;

import individualassignment.edubridge.Domain.Courses.Requests.CreateCourseRequest;
import individualassignment.edubridge.Domain.Courses.Responses.CreateCourseResponse;

public interface CreateCourseUseCase {
    CreateCourseResponse createCourse (CreateCourseRequest request);
}
