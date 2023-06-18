package individualassignment.edubridge.domain.users;

import individualassignment.edubridge.domain.courses.Course;
import individualassignment.edubridge.persistence.courses.entities.CourseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MostFollowedCourses {

    private Course course;
    private Long count;

    public MostFollowedCourses(Course course, Long count) {
        this.course = course;
        this.count = count;
    }
}
