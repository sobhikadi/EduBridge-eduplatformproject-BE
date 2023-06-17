package individualassignment.edubridge.domain.users;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DateCountFollowingStudents {
    private LocalDate date;
    private Long count;

    public DateCountFollowingStudents(LocalDate date, Long count) {
        this.date = date;
        this.count = count;
    }
}
