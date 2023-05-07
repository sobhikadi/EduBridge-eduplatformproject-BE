package individualassignment.edubridge.business.course.impl;

import individualassignment.edubridge.business.course.GetCourseUseCase;
import individualassignment.edubridge.domain.courses.Course;
import individualassignment.edubridge.persistence.courses.CourseRepository;
import individualassignment.edubridge.persistence.courses.entities.CourseEntity;
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
    public Course getCourse(String courseTitle) {
        Optional<CourseEntity> courseEntity = courseRepository.findByTitleContainingIgnoreCase(courseTitle);
        return courseEntity.map(CourseConverter::convert).orElse(null);
    }
}
