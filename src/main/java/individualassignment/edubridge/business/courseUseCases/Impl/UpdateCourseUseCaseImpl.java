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

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateCourseUseCaseImpl implements UpdateCourseUseCase {
    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;
    private final UploadImageService uploadImageService;
    @Transactional
    @Override
    public void updateCourse(UpdateCourseRequest request) {
        Optional<CourseEntity> courseOptional = courseRepository.findById(request.getId());
        if(courseOptional.isEmpty()){
            throw new InvalidCourseIdException();
        }

        CourseEntity course = courseOptional.get();
        CourseEntity updatedCourse = updateFields(request, course);
        courseRepository.save(updatedCourse);
    }

    private CourseEntity updateFields(UpdateCourseRequest request, CourseEntity course) {

        Optional<CategoryEntity> category = categoryRepository.findById(request.getCategoryId());

        if (category.isEmpty()) {
            throw new InvalidCategoryIdException();
        }

        if (!Objects.equals(course.getTitle(), request.getTitle())) {
            uploadImageService.deleteImage(course.getTitle());
        }

        String imageUrl = null;
        if(request.getImage() != null) {
            imageUrl = uploadImageService.uploadImage(request.getImage(), request.getTitle());
        }

        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setProvider(request.getProvider());
        course.setPublishDate(request.getPublishState() == CoursePublishState.PUBLISHED ? LocalDate.now() : null);
        course.setPublishState(request.getPublishState());
        course.setImageUrl(imageUrl);
        course.setLastModified(LocalDateTime.parse(LocalDateTime
                .now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        course.setCategory(category.get());
        return course;
    }

}
