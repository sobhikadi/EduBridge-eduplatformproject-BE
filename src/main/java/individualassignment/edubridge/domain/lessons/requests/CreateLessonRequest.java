package individualassignment.edubridge.domain.lessons.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateLessonRequest {

    @NotBlank
    @Length(min = 3, max = 100)
    private String name;

    @NotBlank
    @Length(min = 3, max = 1000)
    private String description;

    @NotNull
    private Long courseId;

}
