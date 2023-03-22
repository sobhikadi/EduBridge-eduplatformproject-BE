package individualassignment.edubridge.Business.CourseUseCases.Impl;

import individualassignment.edubridge.Business.CourseUseCases.DeleteCategoryUseCase;
import individualassignment.edubridge.Business.CourseUseCases.DeleteCourseUseCase;
import individualassignment.edubridge.Persistence.Courses.CategoryRepository;
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
