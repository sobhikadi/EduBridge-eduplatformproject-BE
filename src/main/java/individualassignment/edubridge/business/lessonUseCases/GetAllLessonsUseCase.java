package individualassignment.edubridge.business.lessonUseCases;

import individualassignment.edubridge.domain.lessons.requests.GetAllLessonsRequest;
import individualassignment.edubridge.domain.lessons.responses.GetAllLessonsResponse;

public interface GetAllLessonsUseCase {
    GetAllLessonsResponse getAllLessonsByCourseId(GetAllLessonsRequest request);
}
