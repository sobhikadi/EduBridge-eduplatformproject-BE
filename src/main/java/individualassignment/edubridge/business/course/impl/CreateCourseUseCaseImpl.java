package individualassignment.edubridge.business.course.impl;

import individualassignment.edubridge.business.category.exceptions.InvalidCategoryIdException;
import individualassignment.edubridge.business.course.CreateCourseUseCase;
import individualassignment.edubridge.business.course.UploadImageService;
import individualassignment.edubridge.business.course.exceptions.CourseNameAlreadyExistsException;
import individualassignment.edubridge.domain.courses.CoursePublishState;
import individualassignment.edubridge.domain.courses.requests.CreateCourseRequest;
import individualassignment.edubridge.domain.courses.responses.CreateCourseResponse;
import individualassignment.edubridge.persistence.categories.CategoryRepository;
import individualassignment.edubridge.persistence.courses.CourseRepository;
import individualassignment.edubridge.persistence.categories.entities.CategoryEntity;
import individualassignment.edubridge.persistence.courses.entities.CourseEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CreateCourseUseCaseImpl implements CreateCourseUseCase {

    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;
    private final UploadImageService uploadImageService;

    @Transactional
    @Override
    public CreateCourseResponse createCourse(CreateCourseRequest request) {
        if(courseRepository.existsByTitle(request.getTitle())) {
            throw new CourseNameAlreadyExistsException();
        }

        CourseEntity savedCourse = saveNewCourse(request);
        return CreateCourseResponse.builder()
                .courseId(savedCourse.getId())
                .build();
    }

    private CourseEntity saveNewCourse(CreateCourseRequest request)
    {
        Optional<CategoryEntity> category = categoryRepository.findById(request.getCategoryId());

        if (category.isEmpty()) {
            throw new InvalidCategoryIdException();
        }

        String imageUrl = null;
        if(request.getImage() != null) {
            imageUrl = uploadImageService.uploadImage(request.getImage(), request.getTitle());
        }

        LocalDate publishDate =
                request.getPublishState() == CoursePublishState.PUBLISHED ? LocalDate.now() : null;

        CourseEntity newCourse = CourseEntity.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .provider(request.getProvider())
                .creationDate(LocalDate.now())
                .publishDate(publishDate)
                .publishState(request.getPublishState())
                .category(category.get())
                .imageUrl(imageUrl)
                .build();

        return courseRepository.save(newCourse);
    }

}
