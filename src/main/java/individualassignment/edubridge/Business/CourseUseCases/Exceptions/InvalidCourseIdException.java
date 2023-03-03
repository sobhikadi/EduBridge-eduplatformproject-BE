package individualassignment.edubridge.Business.CourseUseCases.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidCourseIdException extends ResponseStatusException {
    public InvalidCourseIdException (String errorMessage){
        super(HttpStatus.BAD_REQUEST, errorMessage);
    }
}
