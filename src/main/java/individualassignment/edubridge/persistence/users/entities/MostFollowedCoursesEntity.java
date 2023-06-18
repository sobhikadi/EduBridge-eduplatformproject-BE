package individualassignment.edubridge.persistence.users.entities;

import individualassignment.edubridge.persistence.courses.entities.CourseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MostFollowedCoursesEntity {

        private CourseEntity course;
        private Long count;

        public MostFollowedCoursesEntity(CourseEntity course, Long count) {
            this.course = course;
            this.count = count;
        }
}
