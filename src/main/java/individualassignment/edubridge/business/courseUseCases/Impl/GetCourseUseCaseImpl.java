package individualassignment.edubridge.business.courseUseCases.Impl;

import individualassignment.edubridge.business.courseUseCases.GetCourseUseCase;
import individualassignment.edubridge.domain.courses.Course;
import individualassignment.edubridge.persistence.courses.CourseRepository;
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
