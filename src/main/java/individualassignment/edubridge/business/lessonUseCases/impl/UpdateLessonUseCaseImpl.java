package individualassignment.edubridge.business.lessonUseCases.impl;

import individualassignment.edubridge.business.lessonUseCases.exceptions.InvalidLessonIdException;
import individualassignment.edubridge.business.lessonUseCases.UpdateLessonUseCase;
import individualassignment.edubridge.domain.lessons.requests.UpdateLessonRequest;
import individualassignment.edubridge.persistence.lessons.entities.LessonEntity;
import individualassignment.edubridge.persistence.lessons.LessonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateLessonUseCaseImpl implements UpdateLessonUseCase {

    private final LessonRepository lessonRepository;

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
        lesson.setName(request.getName());
        lesson.setDescription(request.getDescription());
//        if (!request.getLinks().isEmpty()) {
//            for (String linkName : request.getLinks().keySet()) {
//                 if(!lesson.getLinks().containsKey(linkName)) {
//                     lesson.getLinks().put(linkName, request.getLinks().get(linkName));
//                 }
//                 else {
//                     lesson.getLinks().replace(linkName, request.getLinks().get(linkName));
//                 }
//            }
//        }
        return lesson;
    }

}
