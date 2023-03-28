package individualassignment.edubridge.Business.CourseUseCases.Impl;

import individualassignment.edubridge.Business.CourseUseCases.GetAllLessonsUseCase;

import individualassignment.edubridge.Domain.Courses.Lesson;
import individualassignment.edubridge.Domain.Courses.Requests.GetAllLessonsRequest;
import individualassignment.edubridge.Domain.Courses.Responses.GetAllLessonsResponse;
import individualassignment.edubridge.Persistence.Courses.Entity.LessonEntity;
import individualassignment.edubridge.Persistence.Courses.LessonRepository;
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
