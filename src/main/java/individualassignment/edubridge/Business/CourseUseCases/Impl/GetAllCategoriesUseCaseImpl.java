package individualassignment.edubridge.Business.CourseUseCases.Impl;

import individualassignment.edubridge.Business.CourseUseCases.GetAllCategoriesUseCase;
import individualassignment.edubridge.Domain.Courses.Category;
import individualassignment.edubridge.Domain.Courses.Course;
import individualassignment.edubridge.Domain.Courses.Responses.GetAllCategoriesResponse;
import individualassignment.edubridge.Domain.Courses.Responses.GetAllCoursesResponse;
import individualassignment.edubridge.Persistence.Courses.CategoryRepository;
import individualassignment.edubridge.Persistence.Courses.Entity.CategoryEntity;
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
