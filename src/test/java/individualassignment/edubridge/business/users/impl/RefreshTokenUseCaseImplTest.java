package individualassignment.edubridge.business.users.impl;

import individualassignment.edubridge.business.users.AccessTokenEncoder;
import individualassignment.edubridge.business.users.exceptions.InvalidRefreshTokenException;
import individualassignment.edubridge.domain.users.AccessToken;
import individualassignment.edubridge.domain.users.UserRoleEnum;
import individualassignment.edubridge.persistence.users.RefreshTokenRepository;
import individualassignment.edubridge.persistence.users.UserRepository;
import individualassignment.edubridge.persistence.users.entities.RefreshTokenEntity;
import individualassignment.edubridge.persistence.users.entities.UserEntity;
import individualassignment.edubridge.persistence.users.entities.UserRoleEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RefreshTokenUseCaseImplTest {

    @Mock
    private RefreshTokenRepository refreshTokenRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AccessTokenEncoder accessTokenEncoder;
    @InjectMocks
    private RefreshTokenUseCaseImpl refreshTokenUseCase;

    @Test
    void findByToken_ShouldReturnRefreshToken() {
        RefreshTokenEntity expected = RefreshTokenEntity.builder()
                .expiryDate(LocalDateTime.now().plus(7, ChronoUnit.DAYS))
                .token("token")
                .build();

        when(refreshTokenRepository.findByToken("token")).thenReturn(Optional.of(expected));

        Optional<RefreshTokenEntity> actual = refreshTokenUseCase.findByToken("token");

        assertEquals(expected, actual.orElse(null));
    }

    @Test
    void createRefreshToken_ShouldReturnRefreshTokenAndAccessToken() {
        String userName = "testUser";
        UserEntity userEntity = UserEntity.builder()
                .userName(userName)
                .password("password")
                .userRoles(Set.of(UserRoleEntity.builder().role(UserRoleEnum.ADMIN).build()))
                .build();

        String expectedAccessToken = "expectedAccessToken";

        RefreshTokenEntity refreshTokenEntity = RefreshTokenEntity.builder()
                .expiryDate(LocalDateTime.now().plus(7, ChronoUnit.DAYS))
                .token(UUID.randomUUID().toString())
                .user(userEntity)
                .build();

        when(userRepository.findByUserName(userName)).thenReturn(userEntity);
        when(refreshTokenRepository.save(any(RefreshTokenEntity.class))).thenReturn(refreshTokenEntity);
        when(accessTokenEncoder.encode(any(AccessToken.class))).thenReturn(expectedAccessToken);
        HashMap<String, String> result = refreshTokenUseCase.createRefreshToken(userName);

        assertEquals(expectedAccessToken, result.get("accessToken"));
        assertEquals(refreshTokenEntity.getToken(), result.get("refreshToken"));
    }

    @Test
    void validateRefreshToken_WithValidToken_ShouldNotThrowException() {
        RefreshTokenEntity refreshToken = RefreshTokenEntity.builder()
                .expiryDate(LocalDateTime.now().plus(7, ChronoUnit.DAYS))
                .token("token")
                .build();

        when(refreshTokenRepository.findByToken("token")).thenReturn(Optional.of(refreshToken));

        assertDoesNotThrow(() -> refreshTokenUseCase.validateRefreshToken("token"));
    }

    @Test
    void validateRefreshToken_WithInvalidToken_ShouldThrowInvalidRefreshTokenException() {
        when(refreshTokenRepository.findByToken("token")).thenReturn(Optional.empty());

        assertThrows(InvalidRefreshTokenException.class, () -> refreshTokenUseCase.validateRefreshToken("token"));
    }

    @Test
    void deleteByUser_ShouldInvokeDeleteByUser() {
        UserEntity user = UserEntity.builder()
                .userName("subject")
                .build();

        when(userRepository.findByUserName("subject")).thenReturn(user);

        refreshTokenUseCase.deleteByUser("subject");

        verify(refreshTokenRepository).deleteByUser(user);
    }
}
