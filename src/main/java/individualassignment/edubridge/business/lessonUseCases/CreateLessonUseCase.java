package individualassignment.edubridge.business.lessonUseCases;

import individualassignment.edubridge.domain.lessons.requests.CreateLessonRequest;
import individualassignment.edubridge.domain.lessons.responses.CreateLessonResponse;

public interface CreateLessonUseCase {
    CreateLessonResponse createLesson (CreateLessonRequest request);
}
