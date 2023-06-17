package individualassignment.edubridge.business.course;

import individualassignment.edubridge.domain.users.DateCountFollowingStudents;

import java.time.LocalDate;
import java.util.List;

public interface GetCoursesStatsUseCase {

    List<DateCountFollowingStudents> countCoursesFollowed(Long courseId, LocalDate startDate, LocalDate endDate);

}
