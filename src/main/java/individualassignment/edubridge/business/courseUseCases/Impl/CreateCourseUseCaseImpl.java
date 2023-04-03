package individualassignment.edubridge.business.courseUseCases.Impl;

import individualassignment.edubridge.business.categoryUseCases.exceptions.InvalidCategoryIdException;
import individualassignment.edubridge.business.courseUseCases.CreateCourseUseCase;
import individualassignment.edubridge.business.courseUseCases.exceptions.CourseNameAlreadyExistsException;
import individualassignment.edubridge.domain.courses.CoursePublishState;
import individualassignment.edubridge.domain.courses.requests.CreateCourseRequest;
import individualassignment.edubridge.domain.courses.responses.CreateCourseResponse;
import individualassignment.edubridge.persistence.categories.CategoryRepository;
import individualassignment.edubridge.persistence.courses.CourseRepository;
import individualassignment.edubridge.persistence.categories.entities.CategoryEntity;
import individualassignment.edubridge.persistence.courses.entities.CourseEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CreateCourseUseCaseImpl implements CreateCourseUseCase {

    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public CreateCourseResponse createCourse(CreateCourseRequest request) {
        if(courseRepository.existsByName(request.getTitle())) {
            throw new CourseNameAlreadyExistsException();
        }
        if (!categoryRepository.existsById(request.getCategoryId())) {
            throw new InvalidCategoryIdException();
        }

        CourseEntity savedCourse = saveNewCourse(request);
        return CreateCourseResponse.builder()
                .courseId(savedCourse.getId())
                .build();
    }

    private CourseEntity saveNewCourse(CreateCourseRequest request)
    {
        CategoryEntity category = categoryRepository.findById(request.getCategoryId()).get();

        Optional<LocalDate> publishDate =
                request.getPublishState() == CoursePublishState.PUBLISHED ? Optional.of(LocalDate.now()) : null;

        CourseEntity newCourse = CourseEntity.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .provider(request.getProvider())
                .creationDate(LocalDate.now())
                .publishDate(publishDate)
                .publishState(request.getPublishState())
                .category(category)
                .imageUrl(request.getImageUrl())
                .build();
        return courseRepository.saveCourse(newCourse);
    }
}
