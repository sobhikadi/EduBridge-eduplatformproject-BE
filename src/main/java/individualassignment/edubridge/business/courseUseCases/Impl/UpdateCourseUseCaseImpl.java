package individualassignment.edubridge.business.courseUseCases.Impl;

import individualassignment.edubridge.business.categoryUseCases.exceptions.InvalidCategoryIdException;
import individualassignment.edubridge.business.courseUseCases.UploadImageService;
import individualassignment.edubridge.business.courseUseCases.exceptions.InvalidCourseIdException;
import individualassignment.edubridge.business.courseUseCases.UpdateCourseUseCase;
import individualassignment.edubridge.domain.courses.CoursePublishState;
import individualassignment.edubridge.domain.courses.requests.UpdateCourseRequest;
import individualassignment.edubridge.persistence.categories.CategoryRepository;
import individualassignment.edubridge.persistence.categories.entities.CategoryEntity;
import individualassignment.edubridge.persistence.courses.CourseRepository;
import individualassignment.edubridge.persistence.courses.entities.CourseEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateCourseUseCaseImpl implements UpdateCourseUseCase {
    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;
    private final UploadImageService uploadImageService;
    @Override
    public void updateCourse(UpdateCourseRequest request) {
        Optional<CourseEntity> courseOptional = courseRepository.findById(request.getId());
        if(courseOptional.isEmpty()){
            throw new InvalidCourseIdException();
        }

        CourseEntity course = courseOptional.get();
        CourseEntity updatedCourse = updateFields(request, course);
        courseRepository.saveCourse(updatedCourse);
    }

    private CourseEntity updateFields(UpdateCourseRequest request, CourseEntity course) {

        Optional<CategoryEntity> category = categoryRepository.findById(request.getCategoryId());

        if (category.isEmpty()) {
            throw new InvalidCategoryIdException();
        }

        String imageUrl = null;
        if(request.getImage().isPresent()) {
            imageUrl = uploadImageService.uploadImage(request.getImage().orElse(null), request.getTitle());
        }

        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setProvider(request.getProvider());
        course.setPublishDate(request.getPublishState() == CoursePublishState.PUBLISHED ? Optional.of(LocalDate.now()) : Optional.empty());
        course.setPublishState(request.getPublishState());
        course.setImageUrl(Optional.of(imageUrl));
        course.setLastModified(null);
        course.setCategory(category.get());
        return course;
    }

}
