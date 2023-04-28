package individualassignment.edubridge.persistence.users.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "admin")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @NotBlank
    @Length(min = 2, max = 50)
    @Column(name = "first_name")
    private String firstName;

    @NotBlank
    @Length(min = 2, max = 50)
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "last_modified")
    private LocalDateTime lastModified;
}
