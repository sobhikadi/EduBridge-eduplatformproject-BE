package individualassignment.edubridge.business.users;

import individualassignment.edubridge.persistence.users.entities.RefreshTokenEntity;

import java.util.HashMap;
import java.util.Optional;

public interface RefreshTokenUseCase {

    public Optional<RefreshTokenEntity> findByToken(String token);

    public HashMap<String, String> createRefreshToken(String subject);

    public void validateRefreshToken(String token);

    public void deleteByUser(String userName);
}
