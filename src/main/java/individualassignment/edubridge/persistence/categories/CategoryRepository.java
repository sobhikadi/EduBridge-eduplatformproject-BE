package individualassignment.edubridge.persistence.categories;

import individualassignment.edubridge.persistence.categories.entities.CategoryEntity;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    boolean existsByName (String categoryName);
    boolean existsById(long categoryId);
    List<CategoryEntity> findAll ();
    CategoryEntity saveCategory (CategoryEntity category);
    void deleteById (Long categoryId);
    Optional<CategoryEntity>findByName (String categoryName);
    Optional<CategoryEntity>findById (long categoryId);
}
