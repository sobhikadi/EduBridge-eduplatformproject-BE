package individualassignment.edubridge.Business.CourseUseCases.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidLessonIdException extends ResponseStatusException {
    public InvalidLessonIdException()
    {
        super(HttpStatus.BAD_REQUEST, "INVALID_LESSON_ID");
    }
}
