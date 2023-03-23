package individualassignment.edubridge.Business.CourseUseCases.Impl;

import individualassignment.edubridge.Business.CourseUseCases.Exceptions.InvalidCourseIdException;
import individualassignment.edubridge.Business.CourseUseCases.UpdateCourseUseCase;
import individualassignment.edubridge.Domain.Courses.Requests.UpdateCourseRequest;
import individualassignment.edubridge.Persistence.Courses.CourseRepository;
import individualassignment.edubridge.Persistence.Courses.Entity.CourseEntity;
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
