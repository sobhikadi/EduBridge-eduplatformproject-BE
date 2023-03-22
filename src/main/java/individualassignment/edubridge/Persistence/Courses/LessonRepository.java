package individualassignment.edubridge.Persistence.Courses;

import individualassignment.edubridge.Persistence.Courses.Entity.CourseEntity;
import individualassignment.edubridge.Persistence.Courses.Entity.LessonEntity;

import java.util.List;
import java.util.Optional;

public interface LessonRepository {
    boolean existsByName (String lessonTitle);
    boolean existsById(long lessonId);
    List<LessonEntity> findAllByCourseId (Long courseId);
    LessonEntity saveLesson (LessonEntity lesson);
    void deleteById (Long lessonId);
    Optional<LessonEntity> findByTitle (String lessonTitle);
    Optional<LessonEntity>findById (long lessonId);


}
