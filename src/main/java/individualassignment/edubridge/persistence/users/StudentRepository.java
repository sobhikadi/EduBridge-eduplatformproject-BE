package individualassignment.edubridge.persistence.users;

import individualassignment.edubridge.persistence.users.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
}
