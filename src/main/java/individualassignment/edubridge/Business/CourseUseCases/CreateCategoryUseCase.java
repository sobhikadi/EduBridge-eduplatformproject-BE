package individualassignment.edubridge.Business.CourseUseCases;

import individualassignment.edubridge.Domain.Courses.Requests.CreateCategoryRequest;
import individualassignment.edubridge.Domain.Courses.Responses.CreateCategoryResponse;

public interface CreateCategoryUseCase {
    CreateCategoryResponse createCategory (CreateCategoryRequest request);
}
