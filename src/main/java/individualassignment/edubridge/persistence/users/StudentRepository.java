package individualassignment.edubridge.persistence.users;

import individualassignment.edubridge.persistence.users.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

    boolean existsByFirstNameAndLastName(String firstName, String lastName);

    List<StudentEntity> findAllByCountryCodeOrderByIdAsc(String countryCode);

}
