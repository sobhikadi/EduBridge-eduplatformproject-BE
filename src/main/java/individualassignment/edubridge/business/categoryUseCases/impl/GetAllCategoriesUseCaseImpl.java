package individualassignment.edubridge.business.categoryUseCases.impl;

import individualassignment.edubridge.business.categoryUseCases.GetAllCategoriesUseCase;
import individualassignment.edubridge.domain.categories.Category;
import individualassignment.edubridge.domain.categories.responses.GetAllCategoriesResponse;
import individualassignment.edubridge.persistence.courses.CategoryRepository;
import individualassignment.edubridge.persistence.courses.entities.CategoryEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetAllCategoriesUseCaseImpl implements GetAllCategoriesUseCase {

    private final CategoryRepository categoryRepository;

    @Override
    public GetAllCategoriesResponse getAllCategories() {
        List<CategoryEntity> result = categoryRepository.findAll();

        final GetAllCategoriesResponse response = new GetAllCategoriesResponse();
        List<Category> categories = result
                .stream()
                .map(CategoryConverter::convert)
                .toList();
        response.setCategories(categories);
        return response;
    }
}
