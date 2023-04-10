package individualassignment.edubridge.persistence.courses;

import individualassignment.edubridge.persistence.courses.entities.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<CourseEntity, Long> {

    boolean existsByTitle (String courseTitle);
    List<CourseEntity> findAllByProviderContainingIgnoreCase (String provider);
    List<CourseEntity> findAllByCategory (String category);
    Optional<CourseEntity>findByTitleContainingIgnoreCase (String courseTitle);
//    Optional<CourseEntity>findById (long courseId);
}
