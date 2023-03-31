package individualassignment.edubridge.business.lessonUseCases.impl;

import individualassignment.edubridge.business.lessonUseCases.GetLessonUseCase;
import individualassignment.edubridge.domain.lessons.Lesson;
import individualassignment.edubridge.persistence.courses.LessonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetLessonUseCaseImpl implements GetLessonUseCase {

    private final LessonRepository lessonRepository;
    @Override
    public Optional<Lesson> getLesson(String lessonTitle, Long courseId) {

        Optional<Lesson> lesson;

        if(lessonTitle != null){
            lesson = lessonRepository.findByName(lessonTitle).map(LessonConverter::convert);
        }
        else if (courseId != 0){
            lesson = lessonRepository.findByCourseId(courseId).map(LessonConverter::convert);
        }
        else lesson = Optional.empty();

        return lesson;
    }
}
