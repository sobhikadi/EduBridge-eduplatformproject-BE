package individualassignment.edubridge.business.lesson.impl;

import individualassignment.edubridge.business.course.exceptions.InvalidCourseIdException;
import individualassignment.edubridge.business.lesson.UpdateLessonUseCase;
import individualassignment.edubridge.business.lesson.exceptions.InvalidLessonIdException;
import individualassignment.edubridge.domain.lessons.requests.UpdateLessonRequest;
import individualassignment.edubridge.persistence.courses.CourseRepository;
import individualassignment.edubridge.persistence.courses.entities.CourseEntity;
import individualassignment.edubridge.persistence.lessons.LessonRepository;
import individualassignment.edubridge.persistence.lessons.entities.LessonEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
