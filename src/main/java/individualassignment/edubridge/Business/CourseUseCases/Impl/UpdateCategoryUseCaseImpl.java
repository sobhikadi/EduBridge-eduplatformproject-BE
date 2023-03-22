package individualassignment.edubridge.Business.CourseUseCases.Impl;

import individualassignment.edubridge.Business.CourseUseCases.Exceptions.InvalidCategoryIdException;
import individualassignment.edubridge.Business.CourseUseCases.UpdateCategoryUseCase;
import individualassignment.edubridge.Domain.Courses.Requests.UpdateCategoryRequest;
import individualassignment.edubridge.Domain.Courses.Requests.UpdateCourseRequest;
import individualassignment.edubridge.Persistence.Courses.CategoryRepository;
import individualassignment.edubridge.Persistence.Courses.Entity.CategoryEntity;
import individualassignment.edubridge.Persistence.Courses.Entity.CourseEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateCategoryUseCaseImpl implements UpdateCategoryUseCase {

    private final CategoryRepository categoryRepository;

    @Override
    public void updateCategory(UpdateCategoryRequest request) {
        Optional<CategoryEntity> categoryOptional = this.categoryRepository.findById(request.getId());

        if(categoryOptional.isEmpty()) throw new InvalidCategoryIdException();


        CategoryEntity category = categoryOptional.get();
        CategoryEntity updatedCategory = updateFields(request, category);
        categoryRepository.saveCategory(updatedCategory);
    }

    private CategoryEntity updateFields(UpdateCategoryRequest request, CategoryEntity category){
        category.setName(request.getName());
        return category;
    }
}
