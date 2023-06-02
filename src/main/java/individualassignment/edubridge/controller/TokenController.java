package individualassignment.edubridge.controller;

import individualassignment.edubridge.business.users.RefreshTokenUseCase;
import individualassignment.edubridge.domain.users.requests.RefreshTokenRequest;
import individualassignment.edubridge.domain.users.responses.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class TokenController {

    private final RefreshTokenUseCase refreshTokenuseCase;

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refreshToken(@RequestBody @Valid RefreshTokenRequest request) {
        refreshTokenuseCase.validateRefreshToken(request.getRefreshToken());
        Map<String, String> refreshToken =
                refreshTokenuseCase.createRefreshToken(request.getSubject());
        return ResponseEntity.ok().body(LoginResponse.builder()
                .accessToken(refreshToken.get("accessToken"))
                .refreshToken(refreshToken.get("refreshToken"))
                .build());
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Valid @RequestBody RefreshTokenRequest request) {
        refreshTokenuseCase.deleteByUser(request.getSubject());
        return ResponseEntity.ok().build();
    }
}