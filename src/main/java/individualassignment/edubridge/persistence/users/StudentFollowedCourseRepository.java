package individualassignment.edubridge.persistence.users;

import individualassignment.edubridge.persistence.courses.entities.CourseEntity;
import individualassignment.edubridge.persistence.users.entities.StudentEntity;
import individualassignment.edubridge.persistence.users.entities.StudentFollowedCourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentFollowedCourseRepository extends JpaRepository<StudentFollowedCourseEntity, Long> {
    Optional<StudentFollowedCourseEntity> findByStudentAndCourse(StudentEntity student, CourseEntity course);

    void deleteByStudentAndCourse(StudentEntity student, CourseEntity course);

    List<StudentFollowedCourseEntity> findAllByCourse_Id( Long courseId);
}
