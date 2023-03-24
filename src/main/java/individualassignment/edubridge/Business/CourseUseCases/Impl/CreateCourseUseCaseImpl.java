package individualassignment.edubridge.Business.CourseUseCases.Impl;

import individualassignment.edubridge.Business.CourseUseCases.CreateCourseUseCase;
import individualassignment.edubridge.Business.CourseUseCases.Exceptions.CourseNameAlreadyExistsException;
import individualassignment.edubridge.Domain.Courses.Requests.CreateCourseRequest;
import individualassignment.edubridge.Domain.Courses.Responses.CreateCourseResponse;
import individualassignment.edubridge.Persistence.Courses.CourseRepository;
import individualassignment.edubridge.Persistence.Courses.Entity.CourseEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CreateCourseUseCaseImpl implements CreateCourseUseCase {

    private final CourseRepository courseRepository;

    @Override
    public CreateCourseResponse createCourse(CreateCourseRequest request) {
        if(courseRepository.existsByName(request.getTitle())) {
            throw new CourseNameAlreadyExistsException();
        }

        CourseEntity savedCourse = saveNewCourse(request);
        return CreateCourseResponse.builder()
                .courseId(savedCourse.getId())
                .build();
    }

    private CourseEntity saveNewCourse(CreateCourseRequest request)
    {
        String pDate = request.getPublishDate().orElse("");
        Optional<LocalDate> publishDate = pDate != "" ? Optional.ofNullable(LocalDate.parse(pDate)) : null;

        CourseEntity newCourse = CourseEntity.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .provider(request.getProvider())
                .creationDate(LocalDate.parse(request.getCreationDate()))
                .publishDate(publishDate)
                .build();
        return courseRepository.saveCourse(newCourse);
    }
}
