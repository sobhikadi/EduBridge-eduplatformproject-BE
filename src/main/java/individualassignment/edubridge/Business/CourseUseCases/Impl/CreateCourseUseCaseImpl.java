package individualassignment.edubridge.Business.CourseUseCases.Impl;

import individualassignment.edubridge.Business.CourseUseCases.CreateCourseUseCase;
import individualassignment.edubridge.Business.CourseUseCases.Exceptions.CourseNameAlreadyExistsException;
import individualassignment.edubridge.Domain.Courses.Requests.CreateCourseRequest;
import individualassignment.edubridge.Domain.Courses.Responses.CreateCourseResponse;
import individualassignment.edubridge.Persistence.Courses.CourseRepository;
import individualassignment.edubridge.Persistence.Courses.Entity.CourseEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
        CourseEntity newCourse = CourseEntity.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .provider(request.getProvider())
                .build();
        return courseRepository.saveCourse(newCourse);
    }
}
