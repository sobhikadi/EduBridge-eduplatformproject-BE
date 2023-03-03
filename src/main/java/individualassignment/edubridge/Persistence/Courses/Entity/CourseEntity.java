package individualassignment.edubridge.Persistence.Courses.Entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourseEntity {

    private Long id;
    private String title;
    private String description;
    private int nrOfLessons;
    private String provider;
}
