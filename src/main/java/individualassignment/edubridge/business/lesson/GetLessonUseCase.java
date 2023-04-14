package individualassignment.edubridge.business.lesson;

import individualassignment.edubridge.domain.lessons.Lesson;

import java.util.Optional;

public interface GetLessonUseCase {
    Optional<Lesson> getLesson(String lessonTitle, Long courseId);
}
