package individualassignment.edubridge.domain.users.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateStudentResponse {
    private Long studentId;
}
