package individualassignment.edubridge.persistence.categories;

import individualassignment.edubridge.persistence.categories.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    boolean existsByName (String categoryName);
//    boolean existsById(long categoryId);
//    List<CategoryEntity> findAll ();
//    CategoryEntity saveCategory (CategoryEntity category);
//    void deleteById (Long categoryId);
//    Optional<CategoryEntity>findByName (String categoryName);
//    Optional<CategoryEntity>findById (long categoryId);
}
