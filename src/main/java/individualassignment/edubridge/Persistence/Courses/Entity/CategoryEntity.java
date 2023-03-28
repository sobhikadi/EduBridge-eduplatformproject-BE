package individualassignment.edubridge.Persistence.Courses.Entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryEntity {
    private Long id;
    private String name;
}
