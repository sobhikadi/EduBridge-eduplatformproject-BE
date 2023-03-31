package individualassignment.edubridge.business.courseUseCases.Impl;

import individualassignment.edubridge.business.courseUseCases.CreateCourseUseCase;
import individualassignment.edubridge.business.courseUseCases.exceptions.CourseNameAlreadyExistsException;
import individualassignment.edubridge.domain.courses.requests.CreateCourseRequest;
import individualassignment.edubridge.domain.courses.responses.CreateCourseResponse;
import individualassignment.edubridge.persistence.courses.CourseRepository;
import individualassignment.edubridge.persistence.courses.entities.CourseEntity;
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
        Optional<LocalDate> publishDate = !pDate.equals("") ? Optional.ofNullable(LocalDate.parse(pDate)) : null;

        CourseEntity newCourse = CourseEntity.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .provider(request.getProvider())
                .creationDate(LocalDate.parse(request.getCreationDate()))
                .publishDate(publishDate)
                .publishState(request.getPublishState())
                .build();
        return courseRepository.saveCourse(newCourse);
    }
}
