package individualassignment.edubridge.Domain.Courses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;

@Data
@Builder
@AllArgsConstructor
public class Lesson {
    private Long id;
    String title;
    String description;
    HashMap<String, String> links;

}
