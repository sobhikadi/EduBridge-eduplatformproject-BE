package individualassignment.edubridge.business.courseUseCases.Impl;

import individualassignment.edubridge.business.categoryUseCases.impl.CategoryConverter;
import individualassignment.edubridge.business.lessonUseCases.impl.LessonConverter;
import individualassignment.edubridge.domain.courses.Course;
import individualassignment.edubridge.persistence.courses.entities.CourseEntity;

import java.util.Collections;
import java.util.Optional;

public class CourseConverter {
    private CourseConverter(){}

    public static Course convert(CourseEntity course) {
        return Course.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .provider(course.getProvider())
                .creationDate(course.getCreationDate())
                .publishDate(Optional.of(course.getPublishDate()))
                .publishState(course.getPublishState())
                .lastModified(Optional.of(course.getLastModified()))
                .imageUrl(course.getImageUrl())
                .category(CategoryConverter.convert(course.getCategory()))
                .build();
    }
}
