package individualassignment.edubridge.persistence.users;

import individualassignment.edubridge.persistence.users.entities.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {
}
