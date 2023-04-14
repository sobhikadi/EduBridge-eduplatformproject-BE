package individualassignment.edubridge.domain.categories.requests;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
@Data
@Builder
@AllArgsConstructor
public class UpdateCategoryRequest {

    @NonNull
    private Long id;

    @NotBlank
    @Length(min = 3, max = 50)
    private String name;


}
