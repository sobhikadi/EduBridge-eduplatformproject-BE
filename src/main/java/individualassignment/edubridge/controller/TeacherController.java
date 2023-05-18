package individualassignment.edubridge.controller;

import individualassignment.edubridge.business.users.teacher.*;
import individualassignment.edubridge.configuration.security.isauthenticated.IsAuthenticated;
import individualassignment.edubridge.domain.users.Teacher;
import individualassignment.edubridge.domain.users.requests.CreateTeacherRequest;
import individualassignment.edubridge.domain.users.requests.GetAllTeachersRequest;
import individualassignment.edubridge.domain.users.requests.UpdateTeacherRequest;
import individualassignment.edubridge.domain.users.responses.CreateTeacherResponse;
import individualassignment.edubridge.domain.users.responses.GetAllTeachersResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000/")
public class TeacherController {
    private final GetTeacherUseCase getTeacherUseCase;
    private final GetAllTeachersUseCase getAllTeachersUseCase;
    private final DeleteTeacherUseCase deleteTeacherUseCase;
    private final CreateTeacherUseCase createTeacherUseCase;
    private final UpdateTeacherUseCase updateTeacherUseCase;

    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN", "ROLE_TEACHER"})
    @GetMapping("{id}")
    public ResponseEntity<Teacher> getTeacher(@PathVariable(value = "id") final long id) {
        final Optional<Teacher> teacherOptional = getTeacherUseCase.getTeacher(id);
        return teacherOptional.map(teacher -> ResponseEntity.ok().body(teacher))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN"})
    @GetMapping
    public ResponseEntity<GetAllTeachersResponse> getAllTeachers(@RequestParam(value = "country", required = false) String countryCode) {
        GetAllTeachersRequest request = new GetAllTeachersRequest();
        request.setCountryCode(countryCode);
        return ResponseEntity.ok(getAllTeachersUseCase.getAllTeachers(request));
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN", "ROLE_TEACHER"})
    @DeleteMapping("{teacherId}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long teacherId) {
        deleteTeacherUseCase.deleteTeacher(teacherId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping()
    public ResponseEntity<CreateTeacherResponse> createTeacher(@RequestBody @Valid CreateTeacherRequest request) {
        CreateTeacherResponse response = createTeacherUseCase.createTeacher(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN", "ROLE_TEACHER"})
    @PutMapping("{id}")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable("id") long id, @RequestBody @Valid UpdateTeacherRequest request) {
        request.setId(id);
        updateTeacherUseCase.updateTeacher(request);
        return ResponseEntity.noContent().build();
    }




}
