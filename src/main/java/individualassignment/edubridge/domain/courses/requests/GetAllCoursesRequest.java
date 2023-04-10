package individualassignment.edubridge.domain.courses.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllCoursesRequest {
    private String provider;
    private String category;
}
