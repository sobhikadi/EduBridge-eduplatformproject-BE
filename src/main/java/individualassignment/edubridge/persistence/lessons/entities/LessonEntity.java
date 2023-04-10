package individualassignment.edubridge.persistence.lessons.entities;

import individualassignment.edubridge.persistence.courses.entities.CourseEntity;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;

@Entity
@Table(name = "lesson")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LessonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private CourseEntity course;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotBlank
    @Length(min = 3, max = 300)
    @Column(name = "description")
    private String description;


}

//    @OneToMany(mappedBy = "lesson")
//    private HashMap<String, String> links;