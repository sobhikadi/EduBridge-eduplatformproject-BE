package individualassignment.edubridge.Persistence.Courses.Impl;

import individualassignment.edubridge.Persistence.Courses.Entity.CourseEntity;
import individualassignment.edubridge.Persistence.Courses.Entity.LessonEntity;
import individualassignment.edubridge.Persistence.Courses.LessonRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class FakeLessonRepositoryImpl implements LessonRepository {

    private static long NEXT_ID = 1;

    private final List<LessonEntity> savedLessons;

    public FakeLessonRepositoryImpl() { this.savedLessons = new ArrayList<>(); }

    @Override
    public boolean existsByName(String lessonTitle) {
        return this.savedLessons
                .stream()
                .anyMatch(lessonEntity -> lessonEntity.getName().equals(lessonTitle));
    }

    @Override
    public boolean existsById(long lessonId) {
        return this.savedLessons
                .stream()
                .anyMatch(lessonEntity -> lessonEntity.getId().equals(lessonId));
    }

    @Override
    public List<LessonEntity> findAll() {
        return Collections.unmodifiableList(this.savedLessons);
    }

    @Override
    public List<LessonEntity> findAllByCourseId(Long courseId) {
        return this.savedLessons
                .stream()
                .filter(lessonEntity -> lessonEntity.getCourseId() != null && lessonEntity.getCourseId().equals(courseId))
                .toList();
    }

    @Override
    public Optional<LessonEntity> findByCourseId(Long courseId) {
        return this.savedLessons
                .stream()
                .filter(lessonEntity -> lessonEntity.getCourseId() != null && lessonEntity.getCourseId().equals(courseId))
                .findFirst();
    }

    @Override
    public LessonEntity saveLesson(LessonEntity lesson) {

        if(lesson.getId() == null)
        {
            lesson.setId(NEXT_ID);
            NEXT_ID++;
            this.savedLessons.add(lesson);
        }
        return lesson;
    }

    @Override
    public void deleteById(Long lessonId) {
        this.savedLessons.removeIf(lessonEntity -> lessonEntity.getId().equals(lessonId));
    }

    @Override
    public void deleteAllByCourseId(Long courseId) {
        this.savedLessons.removeIf(lessonEntity -> lessonEntity.getCourseId() != null && lessonEntity.getCourseId().equals(courseId));
    }

    @Override
    public Optional<LessonEntity> findByName(String lessonName) {
        return this.savedLessons
                .stream()
                .filter(lessonEntity -> lessonEntity.getName().contains(lessonName))
                .findFirst();
    }

    @Override
    public Optional<LessonEntity> findById(long lessonId) {

        return this.savedLessons
                .stream()
                .filter(lessonEntity -> lessonEntity.getId().equals(lessonId))
                .findFirst();
    }
}
