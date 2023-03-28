package individualassignment.edubridge.Persistence.Courses;

import individualassignment.edubridge.Persistence.Courses.Entity.CourseEntity;
import individualassignment.edubridge.Persistence.Courses.Entity.LessonEntity;

import java.util.List;
import java.util.Optional;

public interface LessonRepository {
    boolean existsByName (String lessonTitle);
    boolean existsById(long lessonId);
    List<LessonEntity> findAll();
    List<LessonEntity> findAllByCourseId (Long courseId);
    Optional<LessonEntity> findByCourseId (Long courseId);
    LessonEntity saveLesson (LessonEntity lesson);
    void deleteById (Long lessonId);
    void deleteAllByCourseId (Long courseId);
    Optional<LessonEntity> findByName (String lessonName);
    Optional<LessonEntity>findById (long lessonId);

}
