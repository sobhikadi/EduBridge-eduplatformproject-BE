package individualassignment.edubridge.Business.CourseUseCases;

import individualassignment.edubridge.Domain.Courses.Requests.CreateLessonRequest;
import individualassignment.edubridge.Domain.Courses.Responses.CreateLessonResponse;

public interface CreateLessonUseCase {
    CreateLessonResponse createLesson (CreateLessonRequest request);
}
