package individualassignment.edubridge.business.category;

import individualassignment.edubridge.domain.categories.requests.CreateCategoryRequest;
import individualassignment.edubridge.domain.categories.responses.CreateCategoryResponse;

public interface CreateCategoryUseCase {
    CreateCategoryResponse createCategory (CreateCategoryRequest request);
}
