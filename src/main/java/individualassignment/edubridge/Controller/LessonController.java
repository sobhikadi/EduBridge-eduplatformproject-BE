package individualassignment.edubridge.Controller;

import individualassignment.edubridge.Business.CourseUseCases.*;
import individualassignment.edubridge.Domain.Courses.Lesson;
import individualassignment.edubridge.Domain.Courses.Requests.*;
import individualassignment.edubridge.Domain.Courses.Responses.CreateLessonResponse;
import individualassignment.edubridge.Domain.Courses.Responses.GetAllLessonsResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
        if(lessonOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(lessonOptional.get());
    }


    @DeleteMapping("{lessonId}/{courseId}")
    public ResponseEntity<Void> deleteLesson(@PathVariable(value = "lessonId", required = false) int lessonId,
                                             @PathVariable(value = "courseId", required = false) int courseId) {
        this.deleteLessonUseCase.deleteLesson(lessonId, courseId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{lessonId}")
    public ResponseEntity<Void> updateLesson(@PathVariable("lessonId") long lessonId, @RequestBody @Valid UpdateLessonRequest request){
        request.setId(lessonId);
        updateLessonUseCase.updateLesson(request);
        return ResponseEntity.noContent().build();
    }
}