package individualassignment.edubridge.Domain.Courses.Requests;

import individualassignment.edubridge.Domain.Courses.PublishState;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCourseRequest {

    @NonNull
    private Long id;
    @NotBlank
    @Length(min = 3, max = 100)
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    @Length(min = 1, max = 200)
    private String provider;
    @NonNull
    private Optional<LocalDate> publishDate;
    @NonNull
    private PublishState publishState;

}
