package individualassignment.edubridge.business.users.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidRefreshTokenException extends ResponseStatusException {
    public InvalidRefreshTokenException() {
        super(HttpStatus.BAD_REQUEST, "Invalid refresh token!");
    }
}
