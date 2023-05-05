package individualassignment.edubridge.business.users.teacher.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidTeacherException extends ResponseStatusException {
    public InvalidTeacherException() {
        super(HttpStatus.BAD_REQUEST, "TEACHER_ID_INVALID");
    }
}
