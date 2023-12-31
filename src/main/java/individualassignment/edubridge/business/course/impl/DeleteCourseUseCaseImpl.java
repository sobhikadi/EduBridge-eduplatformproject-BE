package individualassignment.edubridge.business.course.impl;

import individualassignment.edubridge.business.course.DeleteCourseUseCase;
import individualassignment.edubridge.business.course.UploadImageService;
import individualassignment.edubridge.business.course.exceptions.InvalidCourseIdException;
import individualassignment.edubridge.persistence.courses.CourseRepository;
import individualassignment.edubridge.persistence.courses.entities.CourseEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DeleteCourseUseCaseImpl implements DeleteCourseUseCase {
    private final CourseRepository courseRepository;
    private final UploadImageService uploadImageService;

    @Transactional
    @Override
    public void deleteCourse(long courseId) {
        Optional<CourseEntity> courseEntity = courseRepository.findById(courseId);

        if(courseEntity.isEmpty()){
            throw new InvalidCourseIdException();
        }

        this.courseRepository.deleteById(courseId);
        this.uploadImageService.deleteImage(courseEntity.get().getTitle());
    }
}
