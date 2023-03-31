package individualassignment.edubridge.domain.lessons.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class GetAllLessonsRequest {
    private Long courseId;
}
