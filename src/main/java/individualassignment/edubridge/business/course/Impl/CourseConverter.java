package individualassignment.edubridge.business.course.Impl;

import individualassignment.edubridge.business.category.impl.CategoryConverter;
import individualassignment.edubridge.business.lesson.impl.LessonConverter;
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
                .lastModified(Optional.ofNullable(course.getLastModified()))
                .imageUrl(course.getImageUrl())
                .category(CategoryConverter.convert(course.getCategory()))
                .lessons(course.getLessons().isEmpty() ? Collections.emptyList() : course.getLessons()
                        .stream()
                        .map(LessonConverter::convert)
                        .toList())
                .build();
    }
}
