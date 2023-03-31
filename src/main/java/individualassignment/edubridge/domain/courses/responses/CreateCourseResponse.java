package individualassignment.edubridge.domain.courses.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateCourseResponse {
    private Long courseId;
}
