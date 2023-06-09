package individualassignment.edubridge.persistence.users;

import individualassignment.edubridge.persistence.users.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

    boolean existsByFirstNameAndLastName(String firstName, String lastName);

    List<StudentEntity> findAllByCountryCodeOrderByIdAsc(String countryCode);

    @Query("SELECT s FROM StudentEntity s JOIN s.followedCourses c WHERE c.id = :courseId")
    List<StudentEntity> findStudentsByCourseFollowedCourseId(@Param("courseId") Long courseId);

}
