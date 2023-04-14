package individualassignment.edubridge.business.lesson;

import individualassignment.edubridge.domain.lessons.requests.UpdateLessonRequest;

public interface UpdateLessonUseCase {
    void updateLesson(UpdateLessonRequest request);
}
