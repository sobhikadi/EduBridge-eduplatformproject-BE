package individualassignment.edubridge.Domain.Courses.Requests;


import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.HashMap;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateLessonRequest {

    @NonNull
    private Long id;
    @NotBlank
    @Length(min = 3, max = 100)
    private String name;
    @NotBlank
    private String description;
    private HashMap<String, String> links;

}
