package individualassignment.edubridge.business.users;

import individualassignment.edubridge.domain.users.requests.LoginRequest;
import individualassignment.edubridge.domain.users.responses.LoginResponse;

public interface LoginUseCase {
    LoginResponse login(LoginRequest loginRequest);
}
