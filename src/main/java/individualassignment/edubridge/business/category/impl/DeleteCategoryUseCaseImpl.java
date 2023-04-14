package individualassignment.edubridge.business.category.impl;

import individualassignment.edubridge.business.category.DeleteCategoryUseCase;
import individualassignment.edubridge.persistence.categories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteCategoryUseCaseImpl implements DeleteCategoryUseCase {
    private final CategoryRepository categoryRepository;


    @Override
    public void deleteCategory(long categoryId) {
        this.categoryRepository.deleteById(categoryId);
    }
}
