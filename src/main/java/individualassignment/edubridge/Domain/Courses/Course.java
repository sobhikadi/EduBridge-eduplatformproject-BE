package individualassignment.edubridge.Domain.Courses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
public class Course {
    private Long id;
    private String title;
    private String description;
    private String provider;
    private LocalDate creationDate;
    private Optional<LocalDate> publishDate;
    private Optional<LocalDateTime> lastModified;
//    private List<Lesson> lessons;
//    private Category category;
}
