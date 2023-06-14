package individualassignment.edubridge.controller;

import com.cloudinary.api.exceptions.BadRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import individualassignment.edubridge.business.course.*;
import individualassignment.edubridge.configuration.security.isauthenticated.IsAuthenticated;
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

import javax.annotation.security.RolesAllowed;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;
import java.util.Set;

@RestController
@RequestMapping("/courses")
@AllArgsConstructor
public class CourseController {

    private final CreateCourseUseCase createCourseUseCase;
    private final GetAllCoursesUseCase getAllCoursesUseCase;
    private final GetCourseUseCase getCourseUseCase;
    private final DeleteCourseUseCase deleteCourseUseCase;
    private final UpdateCourseUseCase updateCourseUseCase;
    private final ObjectMapper objectMapper;

    private final Validator validator;


    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN", "ROLE_TEACHER"})
    @PostMapping()
    public ResponseEntity<CreateCourseResponse> createCourse(@Valid @RequestParam("courseInfo") String courseInfo,
                                                            @RequestParam("image") MultipartFile image){
        CreateCourseRequest request;
        try {
            request = objectMapper.readValue(courseInfo, CreateCourseRequest.class);
            request.setImage(image);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Set<ConstraintViolation<CreateCourseRequest>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }


        CreateCourseResponse response = createCourseUseCase.createCourse(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping()
    public ResponseEntity<GetAllCoursesResponse> getAllCourses
            (@RequestParam(value = "searchTerm", required = false) String searchTerm,
                @RequestParam(value = "categoryId", required = false) Long categoryId
             ){
        GetAllCoursesRequest request = GetAllCoursesRequest.builder()
                .searchTerm(searchTerm)
                .categoryId(categoryId)
                .build();
        GetAllCoursesResponse response = getAllCoursesUseCase.getAllCourses(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{title}")
    public ResponseEntity<Course> getCourseByTitle(@PathVariable(value = "title") final String title){
        final Course course = getCourseUseCase.getCourse(title);
        return course != null ? ResponseEntity.ok().body(course) : ResponseEntity.notFound().build();
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN"})
    @DeleteMapping("{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable int courseId) {
        try{
        this.deleteCourseUseCase.deleteCourse(courseId);
        }catch (Exception e){
            String messageType = e.getClass().getSimpleName();
            if(messageType.equals("DataIntegrityViolationException")){
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN", "ROLE_TEACHER"})
    @PutMapping("{courseId}")
    public ResponseEntity<Void> updateCourse(@PathVariable("courseId") long courseId,
                                             @RequestParam("courseInfo") String courseInfo,
                                             @RequestParam("image") MultipartFile image){
        UpdateCourseRequest request;
        try {
            request = objectMapper.readValue(courseInfo, UpdateCourseRequest.class);
            request.setImage(image);
            request.setId(courseId);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Set<ConstraintViolation<UpdateCourseRequest>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        updateCourseUseCase.updateCourse(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
