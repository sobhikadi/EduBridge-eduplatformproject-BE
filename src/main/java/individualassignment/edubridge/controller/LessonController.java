package individualassignment.edubridge.controller;

import individualassignment.edubridge.business.lesson.*;
import individualassignment.edubridge.configuration.security.isauthenticated.IsAuthenticated;
import individualassignment.edubridge.domain.lessons.Lesson;
import individualassignment.edubridge.domain.lessons.requests.CreateLessonRequest;
import individualassignment.edubridge.domain.lessons.requests.GetAllLessonsRequest;
import individualassignment.edubridge.domain.lessons.requests.UpdateLessonRequest;
import individualassignment.edubridge.domain.lessons.responses.CreateLessonResponse;
import individualassignment.edubridge.domain.lessons.responses.GetAllLessonsResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/lessons")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000/")
public class LessonController {
    private final CreateLessonUseCase createLessonUseCase;
    private final DeleteLessonUseCase deleteLessonUseCase;
    private final GetAllLessonsUseCase getAllLessonsUseCase;
    private final GetLessonUseCase getLessonUseCase;
    private final UpdateLessonUseCase updateLessonUseCase;

    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN", "ROLE_TEACHER"})
    @PostMapping()
    public ResponseEntity<CreateLessonResponse> createLesson(@RequestBody @Valid CreateLessonRequest request){
        CreateLessonResponse response = createLessonUseCase.createLesson(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping()
    public ResponseEntity<GetAllLessonsResponse> getAllLessonsOrByCourseId(@RequestParam(value = "courseId", required = false) Long courseId){
        GetAllLessonsRequest request = GetAllLessonsRequest.builder().courseId(courseId).build();
        GetAllLessonsResponse response = getAllLessonsUseCase.getAllLessonsByCourseId(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/lesson/{name}/{courseId}")
    public ResponseEntity<Lesson> getLessonCourseId( @PathVariable(value = "name", required = false) final String name, @PathVariable(value = "courseId", required = false) final Long courseId){
        final Optional<Lesson> lessonOptional = getLessonUseCase.getLesson(name, courseId);
        return lessonOptional.map(lesson -> ResponseEntity.ok().body(lesson)).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN"})
    @DeleteMapping("{lessonId}/{courseId}")
    public ResponseEntity<Void> deleteLesson(@PathVariable(value = "lessonId", required = false) int lessonId,
                                             @PathVariable(value = "courseId", required = false) int courseId) {
        this.deleteLessonUseCase.deleteLesson(lessonId, courseId);
        return ResponseEntity.noContent().build();
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN", "ROLE_TEACHER"})
    @PutMapping("{lessonId}")
    public ResponseEntity<Void> updateLesson(@PathVariable("lessonId") long lessonId, @RequestBody @Valid UpdateLessonRequest request){
        request.setId(lessonId);
        updateLessonUseCase.updateLesson(request);
        return ResponseEntity.noContent().build();
    }
}
