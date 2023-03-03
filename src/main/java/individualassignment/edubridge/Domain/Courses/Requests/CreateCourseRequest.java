package individualassignment.edubridge.Domain.Courses.Requests;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
public class CreateCourseRequest {

    @NotBlank
    @Length(min = 3, max = 100)
    private String title;
    @NotBlank
    private String description;

    @Min(1)
    private int nrOfLessons;

    @NotBlank
    @Length(min = 1, max = 200)
    private String provider;

}
