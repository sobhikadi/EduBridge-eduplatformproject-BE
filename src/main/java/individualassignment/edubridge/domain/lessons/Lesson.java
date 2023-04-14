package individualassignment.edubridge.domain.lessons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor
public class Lesson {
    private Long id;
    private String name;
    private String description;

}
