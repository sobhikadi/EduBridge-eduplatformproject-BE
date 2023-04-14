package individualassignment.edubridge.business.lesson;

import individualassignment.edubridge.domain.lessons.requests.CreateLessonRequest;
import individualassignment.edubridge.domain.lessons.responses.CreateLessonResponse;

public interface CreateLessonUseCase {
    CreateLessonResponse createLesson (CreateLessonRequest request);
}
