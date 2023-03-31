package individualassignment.edubridge.business.courseUseCases.Impl;

import individualassignment.edubridge.business.categoryUseCases.impl.CategoryConverter;
import individualassignment.edubridge.business.lessonUseCases.impl.LessonConverter;
import individualassignment.edubridge.domain.courses.Course;
import individualassignment.edubridge.persistence.courses.entities.CourseEntity;

import java.util.Collections;

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
                .lastModified(course.getLastModified())
                .imageUrl(course.getImageUrl())
                .lessons(
                        course.getLessons() != null && !course.getLessons().isEmpty() ?
                                course.getLessons().stream().map(LessonConverter::convert).toList()
                        : Collections.emptyList()
                        )
                .category(CategoryConverter.convert(course.getCategory()))
                .build();
    }
}
