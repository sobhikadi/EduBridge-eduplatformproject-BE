package individualassignment.edubridge.persistence.courses.impl;

import individualassignment.edubridge.persistence.courses.CategoryRepository;
import individualassignment.edubridge.persistence.courses.entities.CategoryEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class FakeCategoryRepositoryImpl implements CategoryRepository {

    private static long NEXT_ID = 1;
    private final List<CategoryEntity> savedCategories;

    public FakeCategoryRepositoryImpl() { this.savedCategories = new ArrayList<>(); }

    @Override
    public boolean existsByName(String categoryTitle) {
        return this.savedCategories
                .stream()
                .anyMatch(categoryEntity -> categoryEntity.getName().equals(categoryTitle));
    }
    @Override
    public boolean existsById(long categoryId) {
        return this.savedCategories
                .stream()
                .anyMatch(categoryEntity -> categoryEntity.getId().equals(categoryId));
    }
    @Override
    public List<CategoryEntity> findAll() {
        return Collections.unmodifiableList(this.savedCategories);
    }

    @Override
    public CategoryEntity saveCategory(CategoryEntity category) {
        if(category.getId() == null)
        {
            category.setId(NEXT_ID);
            NEXT_ID++;
            this.savedCategories.add(category);
        }
        return category;
    }

    @Override
    public void deleteById(Long categoryId) {
        this.savedCategories.removeIf(categoryEntity -> categoryEntity.getId().equals(categoryId));
    }

    @Override
    public Optional<CategoryEntity> findByName(String categoryName) {
        return this.savedCategories
                .stream()
                .filter(categoryEntity -> categoryEntity.getName().contains(categoryName))
                .findFirst();
    }

    @Override
    public Optional<CategoryEntity> findById(long categoryId) {
        return this.savedCategories
                .stream()
                .filter(categoryEntity -> categoryEntity.getId().equals(categoryId))
                .findFirst();
    }



}
