package individualassignment.edubridge.Domain.Courses.Responses;

import individualassignment.edubridge.Domain.Courses.Lesson;
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
