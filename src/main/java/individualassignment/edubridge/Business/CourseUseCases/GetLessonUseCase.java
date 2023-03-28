package individualassignment.edubridge.Business.CourseUseCases;

import individualassignment.edubridge.Domain.Courses.Course;
import individualassignment.edubridge.Domain.Courses.Lesson;

import java.util.Optional;

public interface GetLessonUseCase {
    Optional<Lesson> getLesson(String lessonTitle, Long courseId);
}
