package individualassignment.edubridge.domain.users;

import individualassignment.edubridge.domain.address.Address;
import individualassignment.edubridge.domain.courses.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {

        private Long id;
        private String firstName;
        private String lastName;
        private String publishName;
        private Address address;
        private LocalDateTime lastModified;
        private List<Course> coursesCreatedBy;
}
