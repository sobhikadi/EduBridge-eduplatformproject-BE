package individualassignment.edubridge.business.lesson.impl;

import individualassignment.edubridge.business.lesson.DeleteLessonUseCase;
import individualassignment.edubridge.persistence.lessons.LessonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class DeleteLessonUseCaseImpl implements DeleteLessonUseCase {

    private final LessonRepository lessonRepository;


    @Override
    @Transactional
    public void deleteLesson(long lessonId, long courseId) {
        if (lessonId != 0) {
            this.lessonRepository.deleteById(lessonId);
        } else if (courseId != 0) {
            this.lessonRepository.deleteAllByCourseId(courseId);
        }
    }
}
