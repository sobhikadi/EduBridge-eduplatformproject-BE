package individualassignment.edubridge.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import individualassignment.edubridge.business.courseUseCases.*;
import individualassignment.edubridge.domain.courses.Course;
import individualassignment.edubridge.domain.courses.requests.CreateCourseRequest;
import individualassignment.edubridge.domain.courses.requests.GetAllCoursesRequest;
import individualassignment.edubridge.domain.courses.requests.UpdateCourseRequest;
import individualassignment.edubridge.domain.courses.responses.CreateCourseResponse;
import individualassignment.edubridge.domain.courses.responses.GetAllCoursesResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    private final ObjectMapper objectMapper;


    @PostMapping()
    public ResponseEntity<CreateCourseResponse> createCourse(@RequestParam("courseInfo") String courseInfo,
                                                            @RequestParam("image") MultipartFile image){

        CreateCourseRequest request = null;
        try {
            request = objectMapper.readValue(courseInfo, CreateCourseRequest.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        request.setImage(Optional.of(image));

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
    public ResponseEntity<Void> updateCourse(@PathVariable("courseId") long courseId,
                                             @RequestParam("courseInfo") String courseInfo,
                                             @RequestParam("image") MultipartFile image){
        UpdateCourseRequest request = null;
        try {
            request = objectMapper.readValue(courseInfo, UpdateCourseRequest.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        request.setImage(Optional.of(image));
        request.setId(courseId);
        updateCourseUseCase.updateCourse(request);
        return ResponseEntity.noContent().build();
    }
}
