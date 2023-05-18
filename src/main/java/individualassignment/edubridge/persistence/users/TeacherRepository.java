package individualassignment.edubridge.persistence.users;

import individualassignment.edubridge.persistence.users.entities.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherRepository extends JpaRepository<TeacherEntity, Long> {
    boolean existsByFirstNameAndLastName(String firstName, String lastName);
    List<TeacherEntity> findAllByAddress_CountryCodeOrderByIdAsc(String countryCode);

}
