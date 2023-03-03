package individualassignment.edubridge.Business.CourseUseCases.Impl;

import individualassignment.edubridge.Business.CourseUseCases.GetCourseUseCase;
import individualassignment.edubridge.Domain.Courses.Course;
import individualassignment.edubridge.Persistence.Courses.CourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetCourseUseCaseImpl implements GetCourseUseCase {

    private CourseRepository courseRepository;

    @Override
    public Optional<Course> getCourse(String courseTitle) {
        return courseRepository.findByTitle(courseTitle).map(CourseConverter::convert);
    }
}
