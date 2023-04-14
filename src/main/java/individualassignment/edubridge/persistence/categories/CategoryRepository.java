package individualassignment.edubridge.persistence.categories;

import individualassignment.edubridge.persistence.categories.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    boolean existsByName (String categoryName);
    CategoryEntity findByName (String categoryName);
}
