package individualassignment.edubridge.Business.CourseUseCases.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class LessonNameAlreadyExistsException extends ResponseStatusException {
    public LessonNameAlreadyExistsException()
    {
        super(HttpStatus.BAD_REQUEST, "LESSON_NAME_ALREADY_EXISTS");
    }
}
