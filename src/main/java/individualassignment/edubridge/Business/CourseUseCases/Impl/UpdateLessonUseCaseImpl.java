package individualassignment.edubridge.Business.CourseUseCases.Impl;

import individualassignment.edubridge.Business.CourseUseCases.Exceptions.InvalidLessonIdException;
import individualassignment.edubridge.Business.CourseUseCases.UpdateLessonUseCase;
import individualassignment.edubridge.Domain.Courses.Requests.UpdateLessonRequest;
import individualassignment.edubridge.Persistence.Courses.Entity.LessonEntity;
import individualassignment.edubridge.Persistence.Courses.LessonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
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
        lessonRepository.saveLesson(updatedLesson);
    }

    private LessonEntity updateFields(UpdateLessonRequest request, LessonEntity lesson) {
        lesson.setName(request.getName());
        lesson.setDescription(request.getDescription());
        if (!request.getLinks().isEmpty()) {
            for (String linkName : request.getLinks().keySet()) {
                 if(!lesson.getLinks().containsKey(linkName)) {
                     lesson.getLinks().put(linkName, request.getLinks().get(linkName));
                 }
                 else {
                     lesson.getLinks().replace(linkName, request.getLinks().get(linkName));
                 }
            }
        }
        return lesson;
    }

}
