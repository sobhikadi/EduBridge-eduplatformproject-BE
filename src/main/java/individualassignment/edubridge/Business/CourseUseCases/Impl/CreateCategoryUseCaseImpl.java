package individualassignment.edubridge.Business.CourseUseCases.Impl;

import individualassignment.edubridge.Business.CourseUseCases.CreateCategoryUseCase;
import individualassignment.edubridge.Business.CourseUseCases.Exceptions.CategoryNameAlreadyExistsException;
import individualassignment.edubridge.Domain.Courses.Requests.CreateCategoryRequest;
import individualassignment.edubridge.Domain.Courses.Responses.CreateCategoryResponse;
import individualassignment.edubridge.Persistence.Courses.CategoryRepository;
import individualassignment.edubridge.Persistence.Courses.Entity.CategoryEntity;
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
        return categoryRepository.saveCategory(newCategory);
    }
}
