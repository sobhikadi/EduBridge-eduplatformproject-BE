package individualassignment.edubridge.business.users.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidCountryException extends ResponseStatusException {
    public InvalidCountryException() {
        super(HttpStatus.BAD_REQUEST, "COUNTRY_CODE_INVALID");
    }
}
