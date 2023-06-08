package individualassignment.edubridge.business.users.student.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CourseHasBeenAlredyAddedToFavouritesException extends ResponseStatusException {
    public CourseHasBeenAlredyAddedToFavouritesException() {
        super(HttpStatus.BAD_REQUEST, "YOU_HAVE_ALREADY_ADDED_THIS_COURSE_TO_YOUR_FAVOURITES");
    }
}
