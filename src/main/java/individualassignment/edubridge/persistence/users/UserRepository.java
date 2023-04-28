package individualassignment.edubridge.persistence.users;

import individualassignment.edubridge.persistence.users.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUserName(String username);
    boolean existsByUserName(String username);
}
