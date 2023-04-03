package individualassignment.edubridge.business.lessonUseCases.impl;

import individualassignment.edubridge.business.lessonUseCases.DeleteLessonUseCase;
import individualassignment.edubridge.persistence.lessons.LessonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteLessonUseCaseImpl implements DeleteLessonUseCase {

    private final LessonRepository lessonRepository;

    @Override
    public void deleteLesson(long lessonId, long courseId) {
        if (lessonId != 0) {
            this.lessonRepository.deleteById(lessonId);
        } else {
            this.lessonRepository.deleteAllByCourseId(courseId);
        }
    }
}
