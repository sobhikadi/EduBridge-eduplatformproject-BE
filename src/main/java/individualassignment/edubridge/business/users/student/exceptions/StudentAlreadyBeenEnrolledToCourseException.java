package individualassignment.edubridge.business.users.student.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class StudentAlreadyBeenEnrolledToCourseException extends ResponseStatusException {
    public StudentAlreadyBeenEnrolledToCourseException() {
        super(HttpStatus.BAD_REQUEST, "YOU_HAVE_ALREADY_BEEN_ENROLLED_TO_THIS_COURSE");
    }
}
