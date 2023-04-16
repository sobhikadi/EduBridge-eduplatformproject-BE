package individualassignment.edubridge.business.lesson.impl;

import individualassignment.edubridge.business.course.exceptions.InvalidCourseIdException;
import individualassignment.edubridge.business.lesson.CreateLessonUseCase;
import individualassignment.edubridge.business.lesson.exceptions.LessonNameAlreadyExistsException;
import individualassignment.edubridge.domain.lessons.requests.CreateLessonRequest;
import individualassignment.edubridge.domain.lessons.responses.CreateLessonResponse;
import individualassignment.edubridge.persistence.courses.CourseRepository;
import individualassignment.edubridge.persistence.courses.entities.CourseEntity;
import individualassignment.edubridge.persistence.lessons.entities.LessonEntity;
import individualassignment.edubridge.persistence.lessons.LessonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CreateLessonUseCaseImpl implements CreateLessonUseCase {
    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    @Override
    public CreateLessonResponse createLesson(CreateLessonRequest request) {
        if(lessonRepository.existsByName(request.getName())) {
            throw new LessonNameAlreadyExistsException();
        }

        LessonEntity savedLesson = saveNewLesson(request);
        return CreateLessonResponse.builder()
                .lessonId(savedLesson.getId())
                .build();
    }

    private LessonEntity saveNewLesson(CreateLessonRequest request)
    {
        Optional<CourseEntity> course = courseRepository.findById(request.getCourseId());

        if(course.isEmpty()){
            throw new InvalidCourseIdException();
        }

        LessonEntity newLesson = LessonEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .course(course.get())
                .build();
        return lessonRepository.save(newLesson);
    }
}