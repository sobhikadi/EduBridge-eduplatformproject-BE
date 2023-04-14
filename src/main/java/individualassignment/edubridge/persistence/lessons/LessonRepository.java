package individualassignment.edubridge.persistence.lessons;

import individualassignment.edubridge.persistence.lessons.entities.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LessonRepository extends JpaRepository<LessonEntity, Long> {
    boolean existsByName (String lessonTitle);
    void deleteAllByCourseId (Long courseId);
    List<LessonEntity> findAllByCourseId (Long courseId);
    Optional<LessonEntity> findByName (String lessonName);
    Optional<LessonEntity> findByCourseId (Long courseId);
}
