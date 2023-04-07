package individualassignment.edubridge.business.courseUseCases.Impl;

import individualassignment.edubridge.business.courseUseCases.GetAllCoursesUseCase;
import individualassignment.edubridge.domain.courses.Course;
import individualassignment.edubridge.domain.courses.requests.GetAllCoursesRequest;
import individualassignment.edubridge.domain.courses.responses.GetAllCoursesResponse;
import individualassignment.edubridge.persistence.courses.CourseRepository;
import individualassignment.edubridge.persistence.courses.entities.CourseEntity;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@AllArgsConstructor
public class GetAllCoursesUseCaseImpl implements GetAllCoursesUseCase {

    private CourseRepository courseRepository;

    @Override
    public GetAllCoursesResponse getAllCourses(final GetAllCoursesRequest request) {
        List<CourseEntity> result;

        if(StringUtils.hasText(request.getProvider())){
            result = courseRepository.findAllByProvider(request.getProvider());
        }
        else {
            result = courseRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        }

        final GetAllCoursesResponse response = new GetAllCoursesResponse();
        List<Course> courses = result
                .stream()
                .map(CourseConverter::convert)
                .toList();
        response.setCourses(courses);
        return response;
    }
}
