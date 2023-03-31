package individualassignment.edubridge.business.categoryUseCases.impl;

import individualassignment.edubridge.business.categoryUseCases.DeleteCategoryUseCase;
import individualassignment.edubridge.persistence.courses.CategoryRepository;
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
