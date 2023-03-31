package individualassignment.edubridge.business.lessonUseCases;

import individualassignment.edubridge.domain.lessons.Lesson;

import java.util.Optional;

public interface GetLessonUseCase {
    Optional<Lesson> getLesson(String lessonTitle, Long courseId);
}
