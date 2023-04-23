package individualassignment.edubridge.domain.address.requests;

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
public class UpdateAddressRequest {
    private Long id;

    @NotBlank
    @Length(min = 2, max = 50)
    private String street;

    @NotBlank
    @Length(min = 2, max = 15)
    private String zipCode;

    @NotBlank
    @Length(min = 2, max = 35)
    private String city;

    @NotNull
    private Long countryId;
}
