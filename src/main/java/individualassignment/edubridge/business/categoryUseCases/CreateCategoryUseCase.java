package individualassignment.edubridge.business.categoryUseCases;

import individualassignment.edubridge.domain.categories.requests.CreateCategoryRequest;
import individualassignment.edubridge.domain.categories.responses.CreateCategoryResponse;

public interface CreateCategoryUseCase {
    CreateCategoryResponse createCategory (CreateCategoryRequest request);
}
