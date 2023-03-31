package individualassignment.edubridge.persistence.courses.entities;

import individualassignment.edubridge.domain.categories.Category;
import individualassignment.edubridge.domain.courses.PublishState;
import individualassignment.edubridge.domain.lessons.Lesson;
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
    private PublishState publishState;
    private Optional<LocalDate> publishDate;
    private Optional<LocalDateTime> lastModified;
    private List<LessonEntity> lessons;
    private CategoryEntity category;
    private Optional<String> imageUrl;
}
