package individualassignment.edubridge.business.categoryUseCases.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CategoryNameAlreadyExistsException extends ResponseStatusException {
    public CategoryNameAlreadyExistsException()
    {
        super(HttpStatus.BAD_REQUEST, "CATEGORY_NAME_ALREADY_EXISTS");
    }
}
