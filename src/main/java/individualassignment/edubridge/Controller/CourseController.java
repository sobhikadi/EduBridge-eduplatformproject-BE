package individualassignment.edubridge.Controller;

import individualassignment.edubridge.Business.CourseUseCases.CreateCourseUseCase;
import individualassignment.edubridge.Domain.Courses.Requests.CreateCourseRequest;
import individualassignment.edubridge.Domain.Courses.Responses.CreateCourseResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/courses")
@AllArgsConstructor
public class CourseController {

    private final CreateCourseUseCase createCourseUseCase;

    @PostMapping()
    public ResponseEntity<CreateCourseResponse> createCourse(@RequestBody @Valid CreateCourseRequest request){
        CreateCourseResponse response = createCourseUseCase.createCourse(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
