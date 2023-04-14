package individualassignment.edubridge.business.course.Impl;

import individualassignment.edubridge.business.course.GetCourseUseCase;
import individualassignment.edubridge.domain.courses.Course;
import individualassignment.edubridge.persistence.courses.CourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GetCourseUseCaseImpl implements GetCourseUseCase {

    private CourseRepository courseRepository;

    @Transactional
    @Override
    public Optional<Course> getCourse(String courseTitle) {
        return courseRepository.findByTitleContainingIgnoreCase(courseTitle).map(CourseConverter::convert);
    }
}
