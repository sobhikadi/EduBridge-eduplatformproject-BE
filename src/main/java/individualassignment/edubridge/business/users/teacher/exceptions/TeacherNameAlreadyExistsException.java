package individualassignment.edubridge.business.users.teacher.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TeacherNameAlreadyExistsException extends ResponseStatusException {
    public TeacherNameAlreadyExistsException() {
        super(HttpStatus.BAD_REQUEST, "TEACHER_NAME_ALREADY_EXISTS");
    }
}
