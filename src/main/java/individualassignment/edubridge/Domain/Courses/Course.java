package individualassignment.edubridge.Domain.Courses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Course {
    private long id;
    private String title;
    private String description;
    private int nrOfLessons;
    private String provider;
}
