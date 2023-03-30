package individualassignment.edubridge.Domain.Courses.Requests;


import individualassignment.edubridge.Domain.Courses.PublishState;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    @NotBlank
    private String creationDate;
    private Optional<String> publishDate;
    @NotNull
    private PublishState publishState;


}
