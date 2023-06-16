package individualassignment.edubridge.business.course.impl;

import individualassignment.edubridge.business.course.GetAllCoursesUseCase;
import individualassignment.edubridge.domain.courses.Course;
import individualassignment.edubridge.domain.courses.requests.GetAllCoursesRequest;
import individualassignment.edubridge.domain.courses.responses.GetAllCoursesResponse;
import individualassignment.edubridge.persistence.categories.CategoryRepository;
import individualassignment.edubridge.persistence.categories.entities.CategoryEntity;
import individualassignment.edubridge.persistence.courses.CourseRepository;
import individualassignment.edubridge.persistence.courses.entities.CourseEntity;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.*;

@Service
@AllArgsConstructor
public class GetAllCoursesUseCaseImpl implements GetAllCoursesUseCase {

    private CourseRepository courseRepository;
    private CategoryRepository categoryRepository;

    @Transactional
    @Override
    public GetAllCoursesResponse getAllCourses(final GetAllCoursesRequest request) {
        List<CourseEntity> result;
        CategoryEntity category = null;
        if (request.getCategoryId() != null) {
            category = categoryRepository.findById(request.getCategoryId()).orElse(null);
        }

        if (category != null && StringUtils.hasText(request.getSearchTerm())) {
            result = courseRepository
                    .findAllByTitleOrProviderContainingIgnoreCaseAndCategory
                            (request.getSearchTerm(), request.getSearchTerm(), category);
        }
        else if(StringUtils.hasText(request.getSearchTerm()) && category == null){
            result = courseRepository.findAllByTitleOrProviderContainingIgnoreCase(request.getSearchTerm(), request.getSearchTerm());
        }
        else if(category != null && !StringUtils.hasText(request.getSearchTerm())){
            result = courseRepository.findAllByCategoryOrderById(category);
        }
        else {
            result = courseRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        }

        final GetAllCoursesResponse response = new GetAllCoursesResponse();
        List<Course> courses = new ArrayList<>();
        if(!result.isEmpty()) {
            courses = result
                    .stream()
                    .map(CourseConverter::convert)
                    .toList();
        }

        if(courses.isEmpty()){
            response.setCourses(Collections.emptyList());
            return response;
        }
        response.setCourses(courses);
        return response;
    }
}
