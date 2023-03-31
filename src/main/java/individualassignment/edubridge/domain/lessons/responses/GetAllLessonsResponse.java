package individualassignment.edubridge.domain.lessons.responses;

import individualassignment.edubridge.domain.lessons.Lesson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllLessonsResponse {
    private List<Lesson> lessons;
}
