package individualassignment.edubridge.domain.courses.requests;

import individualassignment.edubridge.domain.courses.CoursePublishState;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
public class UpdateCourseRequest {

    private Long id;
    @NotBlank
    @Length(min = 3, max = 100)
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    @Length(min = 1, max = 200)
    private String provider;
    @NotNull
    private CoursePublishState publishState;

    private Optional<String> imageUrl;
    @NotNull
    private Long categoryId;

}
