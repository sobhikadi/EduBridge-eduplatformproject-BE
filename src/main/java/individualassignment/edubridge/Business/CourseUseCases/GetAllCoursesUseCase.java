package individualassignment.edubridge.Business.CourseUseCases;

import individualassignment.edubridge.Domain.Courses.Requests.GetAllCoursesRequest;
import individualassignment.edubridge.Domain.Courses.Responses.GetAllCoursesResponse;

public interface GetAllCoursesUseCase {
    GetAllCoursesResponse getAllCourses(GetAllCoursesRequest request);
}
