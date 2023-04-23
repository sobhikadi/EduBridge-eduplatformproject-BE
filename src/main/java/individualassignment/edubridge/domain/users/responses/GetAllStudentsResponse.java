package individualassignment.edubridge.domain.users.responses;

import individualassignment.edubridge.domain.users.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllStudentsResponse {
    private List<Student> students;
}
