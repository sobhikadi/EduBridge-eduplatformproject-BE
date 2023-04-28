package individualassignment.edubridge.domain.users;

import individualassignment.edubridge.domain.address.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {

        private Long id;
        private String firstName;
        private String lastName;
        private Address address;
        private LocalDateTime lastModified;
}
