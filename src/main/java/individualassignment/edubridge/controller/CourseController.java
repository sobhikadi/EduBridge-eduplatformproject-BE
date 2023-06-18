package individualassignment.edubridge.controller;

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
import individualassignment.edubridge.domain.users.DateCountFollowingStudents;
import individualassignment.edubridge.domain.users.MostFollowedCourses;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.List;
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
    private final GetCoursesStatsUseCase getCoursesStatsUseCase;
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
    @IsAuthenticated
    @GetMapping("/stats")
    @RolesAllowed({"ROLE_ADMIN", "ROLE_TEACHER"})
    public ResponseEntity<List<DateCountFollowingStudents>> getCoursesStats
            (@RequestParam(value = "courseId", required = false) Long courseId,
             @RequestParam(value = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                @RequestParam(value = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
            ){
        List<DateCountFollowingStudents> response = getCoursesStatsUseCase.countCoursesFollowed(courseId, startDate, endDate);
        return response.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() : ResponseEntity.ok(response);
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN", "ROLE_TEACHER"})
    @GetMapping("/stats/mostFollowed")
    public ResponseEntity<List<MostFollowedCourses>> getMostFollowedCourses
            (@RequestParam(value = "coursesAmount") Long coursesAmount){
        List<MostFollowedCourses> response = getCoursesStatsUseCase.getMostFollowedCourses(coursesAmount);
        return response.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() : ResponseEntity.ok(response);
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
