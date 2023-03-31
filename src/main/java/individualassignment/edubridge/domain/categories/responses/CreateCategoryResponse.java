package individualassignment.edubridge.domain.categories.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateCategoryResponse {
    private Long categoryId;
}
