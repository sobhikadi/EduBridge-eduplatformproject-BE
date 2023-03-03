package individualassignment.edubridge.Persistence.Courses.Impl;

import individualassignment.edubridge.Persistence.Courses.CourseRepository;
import individualassignment.edubridge.Persistence.Courses.Entity.CourseEntity;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class FakeCourseRepositoryImpl implements CourseRepository {

    private static long NEXT_ID = 1;

    private final List<CourseEntity> savedCourses;

    public FakeCourseRepositoryImpl() { this.savedCourses = new ArrayList<>(); }

    @Override
    public boolean existsByName(String courseTitle) {
        return this.savedCourses
                .stream()
                .anyMatch(courseEntity -> courseEntity.getTitle().equals(courseTitle));
    }

    @Override
    public List<CourseEntity> findAllByProvider(String provider) {
        return this.savedCourses
                .stream()
                .filter(courseEntity -> courseEntity.getProvider().contains(provider))
                .toList();
    }

    @Override
    public CourseEntity saveCourse(CourseEntity course) {
        if(course.getId() == null)
        {
            course.setId(NEXT_ID);
            NEXT_ID++;
            this.savedCourses.add(course);
        }
        return course;
    }

    @Override
    public void deleteById(Long courseId) {
        this.savedCourses.removeIf(courseEntity -> courseEntity.getId().equals(courseId));
    }

    @Override
    public List<CourseEntity> findAll() {
        return Collections.unmodifiableList(this.savedCourses);
    }

    @Override
    public Optional<CourseEntity> findByTitle(String courseTitle) {
        return this.savedCourses
                .stream()
                .filter(courseEntity -> courseEntity.getTitle().contains(courseTitle))
                .findFirst();
    }

    @Override
    public Optional<CourseEntity> findById(long courseId) {
        return this.savedCourses
                .stream()
                .filter(courseEntity -> courseEntity.getId().equals(courseId))
                .findFirst();
    }
}
