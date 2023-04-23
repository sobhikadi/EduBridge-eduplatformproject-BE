package individualassignment.edubridge.persistence.courses;

import individualassignment.edubridge.domain.courses.CoursePublishStateEnum;
import individualassignment.edubridge.persistence.categories.entities.CategoryEntity;
import individualassignment.edubridge.persistence.courses.entities.CourseEntity;
import individualassignment.edubridge.persistence.lessons.LessonRepository;
import individualassignment.edubridge.persistence.lessons.entities.LessonEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CourseRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Test
    void save_shouldSaveCourseWithAllFields() {
        CategoryEntity category = saveCategory("Programming");

        CourseEntity course = CourseEntity.builder()
                .title("Java")
                .description("Java course")
                .provider("EduBridge")
                .creationDate(LocalDate.now())
                .publishState(CoursePublishStateEnum.PENDING)
                .lastModified(null)
                .publishDate(null)
                .imageUrl("https://www.Java.com")
                .category(category)
                .lessons(Collections.emptyList())
                .build();

        CourseEntity savedCourse = courseRepository.save(course);
        assertNotNull(savedCourse.getId());

        LessonEntity lesson1 = LessonEntity.builder()
                .name("Lesson 1")
                .description("Lesson 1 description")
                .course(savedCourse)
                .build();

        LessonEntity lesson2 = LessonEntity.builder()
                .name("Lesson 2")
                .description("Lesson 2 description")
                .course(savedCourse)
                .build();

        lessonRepository.save(lesson1);
        lessonRepository.save(lesson2);

        savedCourse = entityManager.find(CourseEntity.class, savedCourse.getId());

        savedCourse.setLessons(List.of(lesson1, lesson2));

        CourseEntity expectedCourse = CourseEntity.builder()
                .id(savedCourse.getId())
                .title("Java")
                .description("Java course")
                .provider("EduBridge")
                .creationDate(LocalDate.now())
                .publishState(CoursePublishStateEnum.PENDING)
                .lastModified(null)
                .publishDate(null)
                .imageUrl("https://www.Java.com")
                .category(category)
                .lessons(List.of(lesson1, lesson2))
                .build();

        entityManager.flush(); // Makes sure queries are sent to the database
        entityManager.clear();


        assertEquals(expectedCourse, savedCourse);
    }

    private CategoryEntity saveCategory(String name) {
        CategoryEntity category = CategoryEntity.builder()
                .name(name)
                .build();
        entityManager.persist(category);
        return category;
    }



    @Test
    void existsByName() {
    }

    @Test
    void findAllByProvider() {
    }

    @Test
    void findByTitle() {
    }
}