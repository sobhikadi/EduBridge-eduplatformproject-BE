package individualassignment.edubridge.persistence.courses;

import individualassignment.edubridge.persistence.categories.entities.CategoryEntity;
import individualassignment.edubridge.persistence.courses.entities.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<CourseEntity, Long> {

    boolean existsByTitle (String courseTitle);

    @Query("SELECT ce FROM CourseEntity ce WHERE LOWER(ce.title) LIKE LOWER(CONCAT('%', :courseTitle, '%')) OR LOWER(ce.provider) LIKE LOWER(CONCAT('%', :provider, '%'))")
    List<CourseEntity> findAllByTitleOrProviderContainingIgnoreCase(String courseTitle, String provider);
    List<CourseEntity> findAllByCategoryOrderById (CategoryEntity category);

    @Query("SELECT ce FROM CourseEntity ce WHERE (LOWER(ce.title) LIKE LOWER(CONCAT('%', :courseTitle, '%')) OR LOWER(ce.provider) LIKE LOWER(CONCAT('%', :provider, '%'))) AND ce.category = :category")
    List<CourseEntity> findAllByTitleOrProviderContainingIgnoreCaseAndCategory(String courseTitle, String provider, CategoryEntity category);
    Optional<CourseEntity> findByTitleContainingIgnoreCase (String courseTitle);
    Optional<CourseEntity> findByTitleIgnoreCase(String title);
}
