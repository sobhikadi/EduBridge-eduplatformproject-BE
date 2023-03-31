package individualassignment.edubridge.persistence.courses.entities;

import individualassignment.edubridge.domain.courses.PublishState;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
//    private List<Lesson> lessons;
//    private Category category;
//    private Optional<String> imageUrl;
}
