package individualassignment.edubridge.business.course.impl;

import individualassignment.edubridge.business.course.GetCoursesStatsUseCase;
import individualassignment.edubridge.domain.users.DateCountFollowingStudents;
import individualassignment.edubridge.persistence.users.StudentFollowedCourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class GetCourseStatsUseCaseImpl implements GetCoursesStatsUseCase {

    private final StudentFollowedCourseRepository studentFollowedCourseRepository;

    @Override
    public List<DateCountFollowingStudents> countCoursesFollowed(Long courseId, LocalDate startDate, LocalDate endDate) {

        if (courseId == null) {
            return studentFollowedCourseRepository.countByFollowingDateBetween(startDate, endDate);
        } else {
            return studentFollowedCourseRepository.countByCourseAndFollowingDateBetween(courseId, startDate, endDate);
        }
    }
}
