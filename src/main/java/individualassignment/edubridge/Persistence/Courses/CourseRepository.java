package individualassignment.edubridge.Persistence.Courses;

import individualassignment.edubridge.Persistence.Courses.Entity.CourseEntity;

import java.util.List;
import java.util.Optional;

public interface CourseRepository {

    boolean existsByName (String courseTitle);
    List<CourseEntity> findAllByProvider (String provider);
    CourseEntity saveCourse (CourseEntity course);
    void deleteById (Long courseId);
    List<CourseEntity> findAll();
    Optional<CourseEntity>findByTitle (String courseTitle);
    Optional<CourseEntity>findById (long courseId);
}
