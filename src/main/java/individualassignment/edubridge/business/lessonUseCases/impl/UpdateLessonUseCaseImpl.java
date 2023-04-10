package individualassignment.edubridge.business.lessonUseCases.impl;

import individualassignment.edubridge.business.courseUseCases.exceptions.InvalidCourseIdException;
import individualassignment.edubridge.business.lessonUseCases.exceptions.InvalidLessonIdException;
import individualassignment.edubridge.business.lessonUseCases.UpdateLessonUseCase;
import individualassignment.edubridge.domain.lessons.requests.UpdateLessonRequest;
import individualassignment.edubridge.persistence.courses.CourseRepository;
import individualassignment.edubridge.persistence.courses.entities.CourseEntity;
import individualassignment.edubridge.persistence.lessons.entities.LessonEntity;
import individualassignment.edubridge.persistence.lessons.LessonRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateLessonUseCaseImpl implements UpdateLessonUseCase {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    @Override
    public void updateLesson(UpdateLessonRequest request) {
        Optional<LessonEntity> lessonOptional = lessonRepository.findById(request.getId());
        if(lessonOptional.isEmpty()){
            throw new InvalidLessonIdException();
        }

        LessonEntity lesson = lessonOptional.get();
        LessonEntity updatedLesson = updateFields(request, lesson);
        lessonRepository.save(updatedLesson);
    }

    private LessonEntity updateFields(UpdateLessonRequest request, LessonEntity lesson) {
        Optional<CourseEntity> course = courseRepository.findById(request.getCourseId());

        if(course.isEmpty()){
            throw new InvalidCourseIdException();
        }

        lesson.setName(request.getName());
        lesson.setDescription(request.getDescription());
        lesson.setCourse(course.get());
        return lesson;
    }

}
