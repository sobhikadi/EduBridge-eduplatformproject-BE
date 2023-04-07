package individualassignment.edubridge.business.categoryUseCases.impl;

import individualassignment.edubridge.business.categoryUseCases.GetAllCategoriesUseCase;
import individualassignment.edubridge.domain.categories.Category;
import individualassignment.edubridge.domain.categories.responses.GetAllCategoriesResponse;
import individualassignment.edubridge.persistence.categories.CategoryRepository;
import individualassignment.edubridge.persistence.categories.entities.CategoryEntity;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.OrderBy;
import java.util.List;

@Service
@AllArgsConstructor
public class GetAllCategoriesUseCaseImpl implements GetAllCategoriesUseCase {

    private final CategoryRepository categoryRepository;

    @Override
    public GetAllCategoriesResponse getAllCategories() {

        List<CategoryEntity> result = categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));

        final GetAllCategoriesResponse response = new GetAllCategoriesResponse();
        List<Category> categories = result
                .stream()
                .map(CategoryConverter::convert)
                .toList();
        response.setCategories(categories);
        return response;
    }
}
