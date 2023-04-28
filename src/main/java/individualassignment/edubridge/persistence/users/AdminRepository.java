package individualassignment.edubridge.persistence.users;

import individualassignment.edubridge.persistence.users.entities.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<AdminEntity, Long> {
}
