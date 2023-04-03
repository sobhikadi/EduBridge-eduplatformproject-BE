package individualassignment.edubridge.domain.courses.requests;

import individualassignment.edubridge.domain.courses.CoursePublishState;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.util.Optional;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCourseRequest {

    @NotBlank
    @Length(min = 3, max = 100)
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    @Length(min = 1, max = 100)
    private String provider;

    @NotNull
    private CoursePublishState publishState;

    private Optional<String> imageUrl;

    @NotNull
    private Long categoryId;
}
