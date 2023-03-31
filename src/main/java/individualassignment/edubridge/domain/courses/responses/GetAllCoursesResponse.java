package individualassignment.edubridge.domain.courses.responses;

import individualassignment.edubridge.domain.courses.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllCoursesResponse {
    private List<Course> courses;
}
