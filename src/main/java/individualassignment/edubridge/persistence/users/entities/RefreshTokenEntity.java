package individualassignment.edubridge.persistence.users.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_token")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @NotBlank
    @Length(max = 255)
    @Column(name = "token")
    private String token;

    @NotNull
    @Column(name = "expiration_date")
    private LocalDateTime expiryDate;

}
