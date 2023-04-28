package individualassignment.edubridge.business.users.student.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class StudentNameAlreadyExistsException extends ResponseStatusException {
    public StudentNameAlreadyExistsException() {
        super(HttpStatus.BAD_REQUEST, "STUDENT_NAME_ALREADY_EXISTS");
    }
}
