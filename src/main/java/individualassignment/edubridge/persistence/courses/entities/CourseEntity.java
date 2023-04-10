package individualassignment.edubridge.persistence.courses.entities;

import individualassignment.edubridge.domain.courses.CoursePublishState;
import individualassignment.edubridge.persistence.categories.entities.CategoryEntity;
import individualassignment.edubridge.persistence.lessons.entities.LessonEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "course")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Length(min = 3, max = 100)
    @Column(name = "title")
    private String title;

    @NotBlank
    @Length(min = 3, max = 300)
    @Column(name = "description")
    private String description;

    @NotBlank
    @Length(min = 1, max = 100)
    @Column(name = "provider")
    private String provider;

    @NotNull
    @Column(name = "creation_date")
    private LocalDate creationDate;

    @NotNull
    @Column(name = "publish_state")
    private CoursePublishState publishState;

    @Column(name = "publish_date")
    private LocalDate publishDate;

    @Column(name = "last_modified")
    private LocalDateTime lastModified;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<LessonEntity> lessons;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @Column(name = "image_url")
    private String imageUrl;
}
