package individualassignment.edubridge.business.course.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidCourseIdException extends ResponseStatusException {
    public InvalidCourseIdException(){
        super(HttpStatus.BAD_REQUEST, "COURSE_ID_INVALID");
    }
}
