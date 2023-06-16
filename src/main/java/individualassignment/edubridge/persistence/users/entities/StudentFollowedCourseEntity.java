package individualassignment.edubridge.persistence.users.entities;

import individualassignment.edubridge.persistence.courses.entities.CourseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "followed_course")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentFollowedCourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinColumn(name = "student_id")
    private StudentEntity student;

    @NotNull
    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinColumn(name = "course_id")
    private CourseEntity course;

    @NotNull
    @EqualsAndHashCode.Exclude
    @Column(name = "following_date")
    private LocalDate followingDate;

}
