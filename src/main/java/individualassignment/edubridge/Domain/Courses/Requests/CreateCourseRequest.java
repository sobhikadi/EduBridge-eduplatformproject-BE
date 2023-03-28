package individualassignment.edubridge.Domain.Courses.Requests;


import individualassignment.edubridge.Domain.Courses.PublishState;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
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
    @NonNull
    private String creationDate;
    private Optional<String> publishDate;
    @NonNull
    private PublishState publishState;


}
