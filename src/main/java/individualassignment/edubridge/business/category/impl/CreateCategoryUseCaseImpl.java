package individualassignment.edubridge.business.category.impl;

import individualassignment.edubridge.business.category.CreateCategoryUseCase;
import individualassignment.edubridge.business.category.exceptions.CategoryNameAlreadyExistsException;
import individualassignment.edubridge.domain.categories.requests.CreateCategoryRequest;
import individualassignment.edubridge.domain.categories.responses.CreateCategoryResponse;
import individualassignment.edubridge.persistence.categories.CategoryRepository;
import individualassignment.edubridge.persistence.categories.entities.CategoryEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateCategoryUseCaseImpl implements CreateCategoryUseCase {

    private final CategoryRepository categoryRepository;

    @Override
    public CreateCategoryResponse createCategory(CreateCategoryRequest request) {
        if(categoryRepository.existsByName(request.getName())){
            throw new CategoryNameAlreadyExistsException();
        }

        CategoryEntity savedCategory = saveNewCategory(request);
        return CreateCategoryResponse.builder()
                .categoryId(savedCategory.getId())
                .build();
    }

    private CategoryEntity saveNewCategory(CreateCategoryRequest request){
        CategoryEntity newCategory = CategoryEntity.builder()
                .name(request.getName())
                .build();
        return categoryRepository.save(newCategory);
    }
}
