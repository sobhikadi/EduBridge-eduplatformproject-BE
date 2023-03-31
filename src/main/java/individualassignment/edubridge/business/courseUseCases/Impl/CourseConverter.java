package individualassignment.edubridge.business.courseUseCases.Impl;

import individualassignment.edubridge.domain.courses.Course;
import individualassignment.edubridge.persistence.courses.entities.CourseEntity;

public class CourseConverter {
    private CourseConverter(){}

    public static Course convert(CourseEntity course) {
        return Course.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .provider(course.getProvider())
                .creationDate(course.getCreationDate())
                .publishDate(course.getPublishDate())
                .publishState(course.getPublishState())
                .build();
    }
}
