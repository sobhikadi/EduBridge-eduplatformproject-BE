package individualassignment.edubridge.business.users.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UnauthorizedDataAccessException extends ResponseStatusException {
    public UnauthorizedDataAccessException() {
        super(HttpStatus.BAD_REQUEST, "USER_ID_NOT_FROM_LOGGED_IN_USER");
    }
}
