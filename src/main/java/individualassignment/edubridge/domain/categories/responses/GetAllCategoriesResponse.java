package individualassignment.edubridge.domain.categories.responses;

import individualassignment.edubridge.domain.categories.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllCategoriesResponse {
    private List<Category> categories;
}
