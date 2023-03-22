package individualassignment.edubridge.Business.CourseUseCases.Impl;

import individualassignment.edubridge.Business.CourseUseCases.DeleteCourseUseCase;
import individualassignment.edubridge.Persistence.Courses.CourseRepository;
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
