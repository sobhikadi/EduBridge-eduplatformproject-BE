package individualassignment.edubridge.Domain.Courses.Responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateCourseResponse {
    private Long courseId;
}
