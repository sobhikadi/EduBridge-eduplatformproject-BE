package individualassignment.edubridge.business.lessonUseCases.impl;

import individualassignment.edubridge.domain.lessons.Lesson;
import individualassignment.edubridge.persistence.lessons.entities.LessonEntity;

public class LessonConverter {

    private LessonConverter(){}

    public static Lesson convert(LessonEntity lesson) {
        return Lesson.builder()
                .id(lesson.getId())
                .name(lesson.getName())
                .description(lesson.getDescription())
//                .links(lesson.getLinks())
                .build();
    }

}
