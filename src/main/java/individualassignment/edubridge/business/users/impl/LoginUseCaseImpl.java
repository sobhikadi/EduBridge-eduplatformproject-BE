package individualassignment.edubridge.business.users.impl;

import individualassignment.edubridge.business.users.AccessTokenEncoder;
import individualassignment.edubridge.business.users.LoginUseCase;
import individualassignment.edubridge.business.users.RefreshTokenUseCase;
import individualassignment.edubridge.business.users.exceptions.InvalidCredentialsException;
import individualassignment.edubridge.domain.users.AccessToken;
import individualassignment.edubridge.domain.users.requests.LoginRequest;
import individualassignment.edubridge.domain.users.responses.LoginResponse;
import individualassignment.edubridge.persistence.users.UserRepository;
import individualassignment.edubridge.persistence.users.entities.RefreshTokenEntity;
import individualassignment.edubridge.persistence.users.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginUseCaseImpl implements LoginUseCase {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AccessTokenEncoder accessTokenEncoder;

    private final RefreshTokenUseCase refreshTokenUseCase;


    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        UserEntity user = userRepository.findByUserName(loginRequest.getUsername());
        if (user == null) {
            throw new InvalidCredentialsException();
        }

        if (!matchesPassword(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        String accessToken = generateAccessToken(user);
        HashMap<String, String> refreshToken = refreshTokenUseCase.createRefreshToken(user.getUserName());

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.get("refreshToken"))
                .build();
    }

    private boolean matchesPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
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
