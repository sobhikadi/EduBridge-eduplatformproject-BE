package individualassignment.edubridge.Domain.Courses.Responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateLessonResponse {
    private Long lessonId;
}