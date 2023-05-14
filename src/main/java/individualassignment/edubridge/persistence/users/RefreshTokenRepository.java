package individualassignment.edubridge.persistence.users;

import individualassignment.edubridge.persistence.users.entities.RefreshTokenEntity;
import individualassignment.edubridge.persistence.users.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

    public Optional<RefreshTokenEntity> findByToken(String token);

    public void deleteByUser(UserEntity user);

}
