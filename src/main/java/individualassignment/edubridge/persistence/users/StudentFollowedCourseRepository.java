package individualassignment.edubridge.persistence.users;

import individualassignment.edubridge.domain.users.DateCountFollowingStudents;
import individualassignment.edubridge.persistence.courses.entities.CourseEntity;
import individualassignment.edubridge.persistence.users.entities.MostFollowedCoursesEntity;
import individualassignment.edubridge.persistence.users.entities.StudentEntity;
import individualassignment.edubridge.persistence.users.entities.StudentFollowedCourseEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StudentFollowedCourseRepository extends JpaRepository<StudentFollowedCourseEntity, Long> {
    Optional<StudentFollowedCourseEntity> findByStudentAndCourse(StudentEntity student, CourseEntity course);

    void deleteByStudentAndCourse(StudentEntity student, CourseEntity course);

    List<StudentFollowedCourseEntity> findAllByCourse_Id( Long courseId);

    @Query("SELECT new individualassignment.edubridge.domain.users.DateCountFollowingStudents" +
            "(s.followingDate, COUNT(s.student)) " +
            "FROM StudentFollowedCourseEntity s " +
            "WHERE s.followingDate BETWEEN :startDate AND :endDate " +
            "GROUP BY s.followingDate")
    List<DateCountFollowingStudents> countByFollowingDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT new individualassignment.edubridge.domain.users.DateCountFollowingStudents" +
            "(s.followingDate, COUNT(DISTINCT s.student)) " +
            "FROM StudentFollowedCourseEntity s " +
            "WHERE s.course.id = :courseId " +
            "AND s.followingDate BETWEEN :startDate AND :endDate " +
            "GROUP BY s.followingDate")
    List<DateCountFollowingStudents> countByCourseAndFollowingDateBetween(@Param("courseId") Long courseId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT new individualassignment.edubridge.persistence.users.entities.MostFollowedCoursesEntity" +
                    "(s.course, COUNT(DISTINCT s.student))  " +
                    "FROM StudentFollowedCourseEntity s GROUP BY s.course ORDER BY COUNT(s.student) DESC")
    List<MostFollowedCoursesEntity> findMostFollowedCourses(Pageable pageable);
}
