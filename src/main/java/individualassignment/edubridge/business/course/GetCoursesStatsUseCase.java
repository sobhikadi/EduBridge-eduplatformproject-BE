package individualassignment.edubridge.business.course;

import individualassignment.edubridge.domain.users.DateCountFollowingStudents;
import individualassignment.edubridge.domain.users.MostFollowedCourses;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.List;

public interface GetCoursesStatsUseCase {

    List<DateCountFollowingStudents> countCoursesFollowed(Long courseId, LocalDate startDate, LocalDate endDate);

    List<MostFollowedCourses> getMostFollowedCourses(Long coursesAmount);

}
