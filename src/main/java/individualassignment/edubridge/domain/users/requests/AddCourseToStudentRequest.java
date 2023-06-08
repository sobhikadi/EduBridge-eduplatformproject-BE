package individualassignment.edubridge.domain.users.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddCourseToStudentRequest {
    @NotNull
    private Long studentId;
    @NotNull
    private Long courseId;
}
