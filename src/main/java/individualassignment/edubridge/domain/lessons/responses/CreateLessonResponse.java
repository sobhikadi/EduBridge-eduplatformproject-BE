package individualassignment.edubridge.domain.lessons.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateLessonResponse {
    private Long lessonId;
}
