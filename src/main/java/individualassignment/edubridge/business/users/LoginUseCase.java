package individualassignment.edubridge.business.users;

import individualassignment.edubridge.domain.users.requests.LoginRequest;
import individualassignment.edubridge.domain.users.responses.LoginResponse;
import individualassignment.edubridge.persistence.users.entities.UserEntity;

public interface LoginUseCase {
    LoginResponse login(LoginRequest loginRequest);

}
