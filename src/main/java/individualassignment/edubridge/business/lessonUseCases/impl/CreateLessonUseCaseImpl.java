package individualassignment.edubridge.business.lessonUseCases.impl;

import individualassignment.edubridge.business.lessonUseCases.CreateLessonUseCase;
import individualassignment.edubridge.business.lessonUseCases.exceptions.LessonNameAlreadyExistsException;
import individualassignment.edubridge.domain.lessons.requests.CreateLessonRequest;
import individualassignment.edubridge.domain.lessons.responses.CreateLessonResponse;
import individualassignment.edubridge.persistence.lessons.entities.LessonEntity;
import individualassignment.edubridge.persistence.lessons.LessonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
                .build();
        return lessonRepository.save(newLesson);
    }
}
