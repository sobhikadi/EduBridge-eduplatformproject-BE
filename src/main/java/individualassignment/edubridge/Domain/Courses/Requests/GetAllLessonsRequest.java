package individualassignment.edubridge.Domain.Courses.Requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class GetAllLessonsRequest {
    private Long courseId;
}
