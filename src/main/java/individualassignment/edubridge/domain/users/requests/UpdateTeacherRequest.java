package individualassignment.edubridge.domain.users.requests;

import individualassignment.edubridge.domain.address.Address;
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
public class UpdateTeacherRequest {

    private Long id;
    @NotBlank
    @Length(min = 2, max = 100)
    private String userName;

    @NotBlank
    @Length(min = 2, max = 100)
    private String password;

    @NotBlank
    @Length(min = 2, max = 50)
    private String firstName;

    @NotBlank
    @Length(min = 2, max = 50)
    private String lastName;

    @NotBlank
    @Length(min = 2, max = 50)
    private String street;

    @NotBlank
    @Length(min = 2, max = 35)
    private String city;

    @NotBlank
    @Length(min = 2, max = 15)
    private String zipcode;

    @NotNull
    private Long countryId;
}
