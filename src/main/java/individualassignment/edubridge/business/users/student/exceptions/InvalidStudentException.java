package individualassignment.edubridge.business.users.student.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidStudentException extends ResponseStatusException {
    public InvalidStudentException() {
        super(HttpStatus.BAD_REQUEST, "STUDENT_ID_INVALID");
    }
}
