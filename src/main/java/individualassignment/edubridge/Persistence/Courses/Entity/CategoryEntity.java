package individualassignment.edubridge.Persistence.Courses.Entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryEntity {
    private Long id;
    String name;
}
