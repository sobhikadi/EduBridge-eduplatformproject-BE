package individualassignment.edubridge.Business.CourseUseCases.Impl;

import individualassignment.edubridge.Business.CourseUseCases.CreateLessonUseCase;
import individualassignment.edubridge.Business.CourseUseCases.Exceptions.LessonNameAlreadyExistsException;
import individualassignment.edubridge.Domain.Courses.Requests.CreateLessonRequest;
import individualassignment.edubridge.Domain.Courses.Responses.CreateLessonResponse;
import individualassignment.edubridge.Persistence.Courses.Entity.LessonEntity;
import individualassignment.edubridge.Persistence.Courses.LessonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateLessonUseCaseImpl implements CreateLessonUseCase {
    private final LessonRepository lessonRepository;

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
        LessonEntity newLesson = LessonEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .links(request.getLinks())
                .build();
        return lessonRepository.saveLesson(newLesson);
    }
}
