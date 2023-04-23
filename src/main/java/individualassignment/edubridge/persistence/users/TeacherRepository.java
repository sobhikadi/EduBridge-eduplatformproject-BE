package individualassignment.edubridge.persistence.users;

import individualassignment.edubridge.persistence.users.entities.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<TeacherEntity, Long> {
}
