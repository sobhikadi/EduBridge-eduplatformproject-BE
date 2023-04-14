package individualassignment.edubridge.business.course;

import individualassignment.edubridge.domain.courses.requests.GetAllCoursesRequest;
import individualassignment.edubridge.domain.courses.responses.GetAllCoursesResponse;

public interface GetAllCoursesUseCase {
    GetAllCoursesResponse getAllCourses(GetAllCoursesRequest request);
}
