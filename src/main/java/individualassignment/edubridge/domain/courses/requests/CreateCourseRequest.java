package individualassignment.edubridge.domain.courses.requests;

import individualassignment.edubridge.domain.courses.CoursePublishState;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCourseRequest {

    @NotBlank
    @Length(min = 3, max = 100)
    private String title;

    @NotBlank
    @Length(min = 3, max = 300)
    private String description;

    @NotBlank
    @Length(min = 1, max = 100)
    private String provider;

    @NotNull
    private CoursePublishState publishState;

    @NotNull
    private MultipartFile image;

    @NotNull
    private Long categoryId;
}
