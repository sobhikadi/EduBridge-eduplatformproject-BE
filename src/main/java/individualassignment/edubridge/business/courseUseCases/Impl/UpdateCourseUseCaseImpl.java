package individualassignment.edubridge.business.courseUseCases.Impl;

import individualassignment.edubridge.business.courseUseCases.exceptions.InvalidCourseIdException;
import individualassignment.edubridge.business.courseUseCases.UpdateCourseUseCase;
import individualassignment.edubridge.domain.courses.requests.UpdateCourseRequest;
import individualassignment.edubridge.persistence.courses.CourseRepository;
import individualassignment.edubridge.persistence.courses.entities.CourseEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateCourseUseCaseImpl implements UpdateCourseUseCase {
    private final CourseRepository courseRepository;
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
        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setProvider(request.getProvider());
        course.setPublishDate(request.getPublishDate());


        return course;
    }

}
