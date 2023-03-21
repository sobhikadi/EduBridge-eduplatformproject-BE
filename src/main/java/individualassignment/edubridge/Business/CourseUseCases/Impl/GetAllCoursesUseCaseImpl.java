package individualassignment.edubridge.Business.CourseUseCases.Impl;

import individualassignment.edubridge.Business.CourseUseCases.GetAllCoursesUseCase;
import individualassignment.edubridge.Domain.Courses.Course;
import individualassignment.edubridge.Domain.Courses.Requests.GetAllCoursesRequest;
import individualassignment.edubridge.Domain.Courses.Responses.GetAllCoursesResponse;
import individualassignment.edubridge.Persistence.Courses.CourseRepository;
import individualassignment.edubridge.Persistence.Courses.Entity.CourseEntity;
import lombok.AllArgsConstructor;
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
            result = courseRepository.findAll();
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
