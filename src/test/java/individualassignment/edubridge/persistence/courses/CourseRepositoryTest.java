package individualassignment.edubridge.persistence.courses;

import individualassignment.edubridge.domain.courses.CoursePublishState;
import individualassignment.edubridge.persistence.categories.entities.CategoryEntity;
import individualassignment.edubridge.persistence.courses.entities.CourseEntity;
import individualassignment.edubridge.persistence.lessons.entities.LessonEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;

import java.time.LocalDate;
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

    @Test
    void save_shouldSaveCourseWithAllFields() {
        CategoryEntity category = saveCategory("Programming");

        LessonEntity lesson1 = LessonEntity.builder()
                .name("Lesson 1")
                .description("Lesson 1 description")
                .build();

        LessonEntity lesson2 = LessonEntity.builder()
                .name("Lesson 2")
                .description("Lesson 2 description")
                .build();

        CourseEntity course = CourseEntity.builder()
                .title("Java")
                .description("Java course")
                .provider("EduBridge")
                .creationDate(LocalDate.now())
                .publishState(CoursePublishState.PENDING)
                .lastModified(null)
                .publishDate(null)
                .imageUrl("https://www.Java.com")
                .category(category)
                .lessons(List.of(lesson1, lesson2))
                .build();

        CourseEntity savedCourse = courseRepository.save(course);
        assertNotNull(savedCourse.getId());

        savedCourse = entityManager.find(CourseEntity.class, savedCourse.getId());

        CourseEntity expectedCourse = CourseEntity.builder()
                .id(savedCourse.getId())
                .title("Java")
                .description("Java course")
                .provider("EduBridge")
                .creationDate(LocalDate.now())
                .publishState(CoursePublishState.PENDING)
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