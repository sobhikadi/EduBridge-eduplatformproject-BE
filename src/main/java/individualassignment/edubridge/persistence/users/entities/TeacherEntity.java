package individualassignment.edubridge.persistence.users.entities;

import individualassignment.edubridge.persistence.address.entities.AddressEntity;
import individualassignment.edubridge.persistence.courses.entities.CourseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "teacher")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeacherEntity {

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

    @NotBlank
    @Length(min = 2, max = 50)
    @Column(name = "publish_name")
    private String publishName;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "address_id")
    private AddressEntity address;

    @Column(name = "last_modified")
    private LocalDateTime lastModified;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "courses_created_by_teacher",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<CourseEntity> coursesCreatedBy;

}
