package individualassignment.edubridge.business.users.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserNameAlreadyExistsException extends ResponseStatusException {
    public UserNameAlreadyExistsException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
