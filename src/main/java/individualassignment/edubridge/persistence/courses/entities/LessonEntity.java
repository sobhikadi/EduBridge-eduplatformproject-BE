package individualassignment.edubridge.persistence.courses.entities;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;

@Data
@Builder
public class LessonEntity {
    private Long id;
    private Long courseId;
    private String name;
    private String description;
    private HashMap<String, String> links;
}
