package individualassignment.edubridge.Persistence.Courses.Entity;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;

@Data
@Builder
public class LessonEntity {
    private Long id;
    String title;
    String description;
    HashMap<String, String> links;
}
