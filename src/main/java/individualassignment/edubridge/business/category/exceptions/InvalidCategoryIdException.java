package individualassignment.edubridge.business.category.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidCategoryIdException extends ResponseStatusException {
    public InvalidCategoryIdException () {
        super(HttpStatus.BAD_REQUEST, "CATEGORY_ID_INVALID");
    }
}
