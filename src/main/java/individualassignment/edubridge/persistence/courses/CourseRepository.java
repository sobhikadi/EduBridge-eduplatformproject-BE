package individualassignment.edubridge.persistence.courses;

import individualassignment.edubridge.persistence.categories.entities.CategoryEntity;
import individualassignment.edubridge.persistence.courses.entities.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<CourseEntity, Long> {

    boolean existsByTitle (String courseTitle);
    List<CourseEntity> findAllByProviderContainingIgnoreCase (String provider);
    List<CourseEntity> findAllByCategoryOrderById (CategoryEntity category);
    Optional<CourseEntity>findByTitleContainingIgnoreCase (String courseTitle);
    Optional<CourseEntity> findByTitleIgnoreCase(String title);
}
