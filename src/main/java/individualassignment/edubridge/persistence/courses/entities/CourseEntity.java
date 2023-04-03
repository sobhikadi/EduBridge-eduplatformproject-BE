package individualassignment.edubridge.persistence.courses.entities;

import individualassignment.edubridge.domain.courses.CoursePublishState;
import individualassignment.edubridge.persistence.categories.entities.CategoryEntity;
import individualassignment.edubridge.persistence.lessons.entities.LessonEntity;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Data
@Builder
public class CourseEntity {

    private Long id;
    private String title;
    private String description;
    private String provider;
    private LocalDate creationDate;
    private CoursePublishState publishState;
    private Optional<LocalDate> publishDate;
    private Optional<LocalDateTime> lastModified;
    private List<LessonEntity> lessons;
    private CategoryEntity category;
    private Optional<String> imageUrl;
}
