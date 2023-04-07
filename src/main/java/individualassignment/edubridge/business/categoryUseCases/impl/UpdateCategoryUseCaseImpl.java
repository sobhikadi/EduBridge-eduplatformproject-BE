package individualassignment.edubridge.business.categoryUseCases.impl;

import individualassignment.edubridge.business.categoryUseCases.exceptions.InvalidCategoryIdException;
import individualassignment.edubridge.business.categoryUseCases.UpdateCategoryUseCase;
import individualassignment.edubridge.domain.categories.requests.UpdateCategoryRequest;
import individualassignment.edubridge.persistence.categories.CategoryRepository;
import individualassignment.edubridge.persistence.categories.entities.CategoryEntity;
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
        categoryRepository.save(updatedCategory);
    }

    private CategoryEntity updateFields(UpdateCategoryRequest request, CategoryEntity category){
        category.setName(request.getName());
        return category;
    }
}
