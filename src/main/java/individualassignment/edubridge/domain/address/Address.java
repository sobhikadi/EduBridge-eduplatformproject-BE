package individualassignment.edubridge.domain.address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    private Long id;
    private String street;
    private String zipCode;
    private String city;
    private Country country;
}
