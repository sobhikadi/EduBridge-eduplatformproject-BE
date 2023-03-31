package individualassignment.edubridge.persistence.courses;

import individualassignment.edubridge.persistence.courses.entities.CourseEntity;

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
