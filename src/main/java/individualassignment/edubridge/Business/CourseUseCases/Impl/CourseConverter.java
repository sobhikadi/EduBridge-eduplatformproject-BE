package individualassignment.edubridge.Business.CourseUseCases.Impl;

import individualassignment.edubridge.Domain.Courses.Course;
import individualassignment.edubridge.Persistence.Courses.Entity.CourseEntity;

public class CourseConverter {
    private CourseConverter(){}

    public static Course convert(CourseEntity course) {
        return Course.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .nrOfLessons(course.getNrOfLessons())
                .provider(course.getProvider())
                .build();
    }
}
