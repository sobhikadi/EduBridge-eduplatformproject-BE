package individualassignment.edubridge.Controller;

import individualassignment.edubridge.Business.CourseUseCases.*;
import individualassignment.edubridge.Domain.Courses.Course;
import individualassignment.edubridge.Domain.Courses.Requests.CreateCourseRequest;
import individualassignment.edubridge.Domain.Courses.Requests.GetAllCoursesRequest;
import individualassignment.edubridge.Domain.Courses.Requests.UpdateCourseRequest;
import individualassignment.edubridge.Domain.Courses.Responses.CreateCourseResponse;
import individualassignment.edubridge.Domain.Courses.Responses.GetAllCoursesResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000/")
public class CourseController {

    private final CreateCourseUseCase createCourseUseCase;
    private final GetAllCoursesUseCase getAllCoursesUseCase;
    private final GetCourseUseCase getCourseUseCase;
    private final DeleteCourseUseCase deleteCourseUseCase;
    private final UpdateCourseUseCase updateCourseUseCase;


    @PostMapping()
    public ResponseEntity<CreateCourseResponse> createCourse(@RequestBody @Valid CreateCourseRequest request){
        CreateCourseResponse response = createCourseUseCase.createCourse(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping()
    public ResponseEntity<GetAllCoursesResponse> getAllCourses(@RequestParam(value = "provider", required = false) String provider){
        GetAllCoursesRequest request = GetAllCoursesRequest.builder().provider(provider).build();
        GetAllCoursesResponse response = getAllCoursesUseCase.getAllCourses(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{title}")
    public ResponseEntity<Course> getCourseByTitle(@PathVariable(value = "title") final String title){
        final Optional<Course> courseOptional = getCourseUseCase.getCourse(title);
        if(courseOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(courseOptional.get());
    }

    @DeleteMapping("{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable int courseId) {
        this.deleteCourseUseCase.deleteCourse(courseId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{courseId}")
    public ResponseEntity<Void> updateCourse(@PathVariable("courseId") long courseId, @RequestBody @Valid UpdateCourseRequest request){
        request.setId(courseId);
        updateCourseUseCase.updateCourse(request);
        return ResponseEntity.noContent().build();
    }
}
