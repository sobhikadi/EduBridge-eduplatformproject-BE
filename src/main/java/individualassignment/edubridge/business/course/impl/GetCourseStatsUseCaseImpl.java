package individualassignment.edubridge.business.course.impl;

import individualassignment.edubridge.business.course.GetCoursesStatsUseCase;
import individualassignment.edubridge.domain.users.DateCountFollowingStudents;
import individualassignment.edubridge.domain.users.MostFollowedCourses;
import individualassignment.edubridge.persistence.users.StudentFollowedCourseRepository;
import individualassignment.edubridge.persistence.users.entities.MostFollowedCoursesEntity;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class GetCourseStatsUseCaseImpl implements GetCoursesStatsUseCase {

    private final StudentFollowedCourseRepository studentFollowedCourseRepository;

    @Override
    @Transactional
    public List<DateCountFollowingStudents> countCoursesFollowed(Long courseId, LocalDate startDate, LocalDate endDate) {

        if (courseId == null) {
            return studentFollowedCourseRepository.countByFollowingDateBetween(startDate, endDate);
        } else {
            return studentFollowedCourseRepository.countByCourseAndFollowingDateBetween(courseId, startDate, endDate);
        }
    }

    @Override
    @Transactional
    public List<MostFollowedCourses> getMostFollowedCourses(Long coursesAmount) {
        List<MostFollowedCoursesEntity> result;
        Pageable pageable = null;
        if (coursesAmount == null || coursesAmount < 1) {
            return Collections.emptyList();
        } else {
            pageable = PageRequest.of(0, coursesAmount.intValue());
            result = studentFollowedCourseRepository.findMostFollowedCourses(pageable);
        }
        List<MostFollowedCourses> response = result
                .stream()
                .map(mostFollowedCoursesEntity ->
                        new MostFollowedCourses(
                                CourseConverter.convert(mostFollowedCoursesEntity.getCourse()),
                                mostFollowedCoursesEntity.getCount()))
                .toList();

        return response;
    }

}
