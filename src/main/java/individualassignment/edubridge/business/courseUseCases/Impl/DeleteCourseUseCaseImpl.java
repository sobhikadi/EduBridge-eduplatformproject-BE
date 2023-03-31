package individualassignment.edubridge.business.courseUseCases.Impl;

import individualassignment.edubridge.business.courseUseCases.DeleteCourseUseCase;
import individualassignment.edubridge.persistence.courses.CourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteCourseUseCaseImpl implements DeleteCourseUseCase {
    private final CourseRepository courseRepository;
    @Override
    public void deleteCourse(long courseId) {
        this.courseRepository.deleteById(courseId);
    }
}
