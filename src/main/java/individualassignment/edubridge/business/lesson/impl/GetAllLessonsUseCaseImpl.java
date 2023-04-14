package individualassignment.edubridge.business.lesson.impl;

import individualassignment.edubridge.business.lesson.GetAllLessonsUseCase;

import individualassignment.edubridge.domain.lessons.Lesson;
import individualassignment.edubridge.domain.lessons.requests.GetAllLessonsRequest;
import individualassignment.edubridge.domain.lessons.responses.GetAllLessonsResponse;
import individualassignment.edubridge.persistence.lessons.entities.LessonEntity;
import individualassignment.edubridge.persistence.lessons.LessonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@AllArgsConstructor
public class GetAllLessonsUseCaseImpl implements GetAllLessonsUseCase {
    private final LessonRepository lessonRepository;
    @Override
    public GetAllLessonsResponse getAllLessonsByCourseId(GetAllLessonsRequest request) {
        List<LessonEntity> result;
        if(request.getCourseId() != null){
            result = lessonRepository.findAllByCourseId(request.getCourseId());
        }
        else {
            result = lessonRepository.findAll();
        }

        final GetAllLessonsResponse response = new GetAllLessonsResponse();
        List<Lesson> lessons = result
                .stream()
                .map(LessonConverter::convert)
                .toList();
        response.setLessons(lessons);
        return response;
    }
}
