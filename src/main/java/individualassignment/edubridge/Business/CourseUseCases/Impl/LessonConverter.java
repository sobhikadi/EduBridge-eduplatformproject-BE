package individualassignment.edubridge.Business.CourseUseCases.Impl;

import individualassignment.edubridge.Domain.Courses.Lesson;
import individualassignment.edubridge.Persistence.Courses.Entity.LessonEntity;

public class LessonConverter {

    private LessonConverter(){}

    public static Lesson convert(LessonEntity lesson) {
        return Lesson.builder()
                .id(lesson.getId())
                .name(lesson.getName())
                .description(lesson.getDescription())
                .links(lesson.getLinks())
                .build();
    }

}
