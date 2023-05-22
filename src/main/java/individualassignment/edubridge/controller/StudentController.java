package individualassignment.edubridge.controller;

import individualassignment.edubridge.business.users.student.*;
import individualassignment.edubridge.configuration.security.isauthenticated.IsAuthenticated;
import individualassignment.edubridge.domain.users.Student;
import individualassignment.edubridge.domain.users.requests.CreateStudentRequest;
import individualassignment.edubridge.domain.users.requests.GetAllStudentsRequest;
import individualassignment.edubridge.domain.users.requests.UpdateStudentRequest;
import individualassignment.edubridge.domain.users.responses.CreateStudentResponse;
import individualassignment.edubridge.domain.users.responses.GetAllStudentsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class StudentController {
    private final GetStudentUseCase getStudentUseCase;
    private final GetAllStudentsUseCase getAllStudentsUseCase;
    private final DeleteStudentUseCase deleteStudentUseCase;
    private final CreateStudentUseCase createStudentUseCase;
    private final UpdateStudentUseCase updateStudentUseCase;

    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN", "ROLE_STUDENT"})
    @GetMapping("{id}")
    public ResponseEntity<Student> getStudent(@PathVariable(value = "id") final long id) {
        final Optional<Student> studentOptional = getStudentUseCase.getStudent(id);
        return studentOptional.map(student -> ResponseEntity.ok().body(student))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN"})
    @GetMapping
    public ResponseEntity<GetAllStudentsResponse> getAllStudents(@RequestParam(value = "country", required = false) String countryCode) {
        GetAllStudentsRequest request = new GetAllStudentsRequest();
        request.setCountryCode(countryCode);
        return ResponseEntity.ok(getAllStudentsUseCase.getAllStudents(request));
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN", "ROLE_STUDENT"})
    @DeleteMapping("{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable int studentId) {
        deleteStudentUseCase.deleteStudent(studentId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping()
    public ResponseEntity<CreateStudentResponse> createStudent(@RequestBody @Valid CreateStudentRequest request) {
        CreateStudentResponse response = createStudentUseCase.createStudent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN", "ROLE_STUDENT"})
    @PutMapping("{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable("id") long id, @RequestBody @Valid UpdateStudentRequest request) {
        request.setId(id);
        updateStudentUseCase.updateStudent(request);
        return ResponseEntity.noContent().build();
    }
}
