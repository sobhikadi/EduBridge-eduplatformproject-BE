package individualassignment.edubridge.business.users.impl;

import individualassignment.edubridge.business.users.AccessTokenEncoder;
import individualassignment.edubridge.business.users.RefreshTokenUseCase;
import individualassignment.edubridge.business.users.exceptions.InvalidRefreshTokenException;
import individualassignment.edubridge.domain.users.AccessToken;
import individualassignment.edubridge.persistence.users.RefreshTokenRepository;
import individualassignment.edubridge.persistence.users.UserRepository;
import individualassignment.edubridge.persistence.users.entities.RefreshTokenEntity;
import individualassignment.edubridge.persistence.users.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
public class RefreshTokenUseCaseImpl implements RefreshTokenUseCase {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final AccessTokenEncoder accessTokenEncoder;


    @Override
    public Optional<RefreshTokenEntity> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public Map<String, String> createRefreshToken(String subject) {
        UserEntity user = userRepository.findByUserName(subject);

        Map<String, String> source = new HashMap<>();

        RefreshTokenEntity refreshToken = RefreshTokenEntity.builder()
                        .expiryDate(LocalDateTime.now().plus(7, ChronoUnit.DAYS))
                        .token(UUID.randomUUID().toString())
                        .user(user)
                    .build();

        String accessToken = generateAccessToken(user);
        RefreshTokenEntity savedRefreshToken = refreshTokenRepository.save(refreshToken);

        source.put("accessToken", accessToken);
        source.put("refreshToken", savedRefreshToken.getToken());

        return source;
    }

    @Override
    public void validateRefreshToken(String token) {
        RefreshTokenEntity refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(
                        InvalidRefreshTokenException::new);

        if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new InvalidRefreshTokenException();
        }

    }

    @Override
    @Transactional
    public void deleteByUser(String userName) {
        refreshTokenRepository.deleteByUser(userRepository.findByUserName(userName));
    }

    private String generateAccessToken(UserEntity user) {
        AccessToken accessToken = AccessToken.builder()
                .subject(user.getUserName())
                .build();

        Long studentId = user.getStudent() != null ? user.getStudent().getId() : null;
        Long teacherId = user.getTeacher() != null ? user.getTeacher().getId() : null;
        Long adminId = user.getAdmin() != null ? user.getAdmin().getId() : null;

        List<String> roles = user.getUserRoles().stream()
                .map(userRole -> userRole.getRole().toString())
                .toList();

        accessToken.setRoles(roles);

        if (studentId != null) {
            accessToken.setStudentId(studentId);
        }
        if (teacherId != null) {
            accessToken.setTeacherId(teacherId);
        }
        if (adminId != null) {
            accessToken.setAdminId(adminId);
        }
        return accessTokenEncoder.encode(accessToken);
    }
}
