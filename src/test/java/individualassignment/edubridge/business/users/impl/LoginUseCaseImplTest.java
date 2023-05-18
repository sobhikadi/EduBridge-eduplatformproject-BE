package individualassignment.edubridge.business.users.impl;

import individualassignment.edubridge.business.users.AccessTokenEncoder;
import individualassignment.edubridge.business.users.RefreshTokenUseCase;
import individualassignment.edubridge.business.users.exceptions.InvalidCredentialsException;
import individualassignment.edubridge.domain.users.AccessToken;
import individualassignment.edubridge.domain.users.UserRoleEnum;
import individualassignment.edubridge.domain.users.requests.LoginRequest;
import individualassignment.edubridge.domain.users.responses.LoginResponse;
import individualassignment.edubridge.persistence.users.UserRepository;
import individualassignment.edubridge.persistence.users.entities.UserEntity;
import individualassignment.edubridge.persistence.users.entities.UserRoleEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginUseCaseImplTest {

    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private PasswordEncoder passwordEncoderMock;
    @Mock
    private AccessTokenEncoder accessTokenEncoderMock;

    @Mock
    private RefreshTokenUseCase refreshTokenUseCaseMock;

    @InjectMocks
    private LoginUseCaseImpl loginUseCase;



    @Test
    void login_ValidCredentials_ShouldReturnLoginResponse() {
        UserEntity user = UserEntity.builder()
                .userName("testUser")
                .password("encodedPassword")
                .userRoles(Set.of(UserRoleEntity.builder().role(UserRoleEnum.STUDENT).build()))
                .build();

        when(userRepositoryMock.findByUserName(user.getUserName())).thenReturn(user);
        when(passwordEncoderMock.matches(anyString(), eq(user.getPassword()))).thenReturn(true);
        when(accessTokenEncoderMock.encode(any(AccessToken.class))).thenReturn("accessToken");
        when(refreshTokenUseCaseMock.createRefreshToken(user.getUserName()))
                .thenReturn(new HashMap<>(Map.of("refreshToken", "testRefreshToken")));

        LoginRequest loginRequest = LoginRequest.builder()
                .username(user.getUserName())
                .password("testPassword")
                .build();

        LoginResponse response = loginUseCase.login(loginRequest);

        assertNotNull(response);
        assertEquals("accessToken", response.getAccessToken());
        assertEquals("testRefreshToken", response.getRefreshToken());
    }

    @Test
    void login_InvalidUsername_ShouldThrowInvalidCredentialsException() {
        // Arrange
        when(userRepositoryMock.findByUserName(anyString())).thenReturn(null);

        LoginRequest loginRequest = LoginRequest.builder()
                .username("invalidUsername")
                .password("testPassword")
                .build();

        assertThrows(InvalidCredentialsException.class, () -> loginUseCase.login(loginRequest));
    }

    @Test
    void login_InvalidPassword_ShouldThrowInvalidCredentialsException() {
        UserEntity user = new UserEntity();
        user.setUserName("testUser");
        user.setPassword("encodedPassword");

        when(userRepositoryMock.findByUserName(user.getUserName())).thenReturn(user);
        when(passwordEncoderMock.matches(anyString(), eq(user.getPassword()))).thenReturn(false);

        LoginRequest loginRequest = LoginRequest.builder()
                .username(user.getUserName())
                .password("invalidPassword")
                .build();

        assertThrows(InvalidCredentialsException.class, () -> loginUseCase.login(loginRequest));
    }
}