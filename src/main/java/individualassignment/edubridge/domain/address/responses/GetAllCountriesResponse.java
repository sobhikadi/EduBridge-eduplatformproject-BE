package individualassignment.edubridge.domain.address.responses;

import individualassignment.edubridge.domain.address.Country;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllCountriesResponse {
    private List<Country> countries;
}
