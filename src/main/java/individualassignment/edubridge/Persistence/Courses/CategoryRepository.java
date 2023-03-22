package individualassignment.edubridge.Persistence.Courses;

import individualassignment.edubridge.Domain.Courses.Category;
import individualassignment.edubridge.Persistence.Courses.Entity.CategoryEntity;
import individualassignment.edubridge.Persistence.Courses.Entity.CourseEntity;
import individualassignment.edubridge.Persistence.Courses.Entity.LessonEntity;

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
