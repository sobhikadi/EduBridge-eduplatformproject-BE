package individualassignment.edubridge.business.courseUseCases.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CourseNameAlreadyExistsException extends ResponseStatusException {
    public CourseNameAlreadyExistsException()
    {
        super(HttpStatus.BAD_REQUEST, "COURSE_NAME_ALREADY_EXISTS");
    }
}
