package individualassignment.edubridge.domain.users;

import individualassignment.edubridge.domain.courses.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentFollowedCourse {
    private Long id;
    private Course course;
    private LocalDate followingDate;
}
