package individualassignment.edubridge.domain.courses.requests;

import individualassignment.edubridge.domain.courses.CoursePublishStateEnum;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
    private CoursePublishStateEnum publishState;
    @NotNull
    private MultipartFile image;
    @NotNull
    private Long categoryId;

}
