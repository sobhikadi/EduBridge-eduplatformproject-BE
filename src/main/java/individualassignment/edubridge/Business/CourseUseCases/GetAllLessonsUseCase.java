package individualassignment.edubridge.Business.CourseUseCases;

import individualassignment.edubridge.Domain.Courses.Requests.GetAllLessonsRequest;
import individualassignment.edubridge.Domain.Courses.Responses.GetAllLessonsResponse;

public interface GetAllLessonsUseCase {
    GetAllLessonsResponse getAllLessonsByCourseId(GetAllLessonsRequest request);
}
