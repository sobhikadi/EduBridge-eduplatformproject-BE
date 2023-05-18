package individualassignment.edubridge.business.lesson.impl;

import individualassignment.edubridge.domain.lessons.Lesson;
import individualassignment.edubridge.domain.lessons.requests.GetAllLessonsRequest;
import individualassignment.edubridge.domain.lessons.responses.GetAllLessonsResponse;
import individualassignment.edubridge.persistence.lessons.LessonRepository;
import individualassignment.edubridge.persistence.lessons.entities.LessonEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllLessonsUseCaseImplTest {

    @Mock
    private LessonRepository lessonRepositoryMock;
    @InjectMocks
    private GetAllLessonsUseCaseImpl getAllLessonsUseCase;

    @Test
    void getAllLessonsByCourseId_ShouldReturnAllLessonsForCourse() {
        Long courseId = 1L;
        List<LessonEntity> lessonEntities = List.of(
                LessonEntity.builder().id(1L).name("Lesson 1").description("Description 1").build(),
                LessonEntity.builder().id(2L).name("Lesson 2").description("Description 2").build()
        );

        when(lessonRepositoryMock.findAllByCourseIdOrderById(courseId)).thenReturn(lessonEntities);

        GetAllLessonsRequest request = GetAllLessonsRequest.builder()
                .courseId(courseId)
                .build();

        GetAllLessonsResponse actual = getAllLessonsUseCase.getAllLessonsByCourseId(request);

        List<Lesson> expectedLessons = lessonEntities.stream()
                .map(LessonConverter::convert)
                .toList();
        GetAllLessonsResponse expected = GetAllLessonsResponse.builder()
                .lessons(expectedLessons)
                .build();

        assertEquals(expected, actual);
        verify(lessonRepositoryMock).findAllByCourseIdOrderById(courseId);
        verify(lessonRepositoryMock, never()).findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Test
    void getAllLessonsByCourseId_ShouldReturnAllLessonsWhenCourseIdIsNull() {
        List<LessonEntity> lessonEntities = List.of(
                LessonEntity.builder().id(1L).name("Lesson 1").description("Description 1").build(),
                LessonEntity.builder().id(2L).name("Lesson 2").description("Description 2").build()
        );

        when(lessonRepositoryMock.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(lessonEntities);

        GetAllLessonsRequest request = GetAllLessonsRequest.builder()
                .courseId(null)
                .build();

        GetAllLessonsResponse actual = getAllLessonsUseCase.getAllLessonsByCourseId(request);

        List<Lesson> expectedLessons = lessonEntities.stream()
                .map(LessonConverter::convert)
                .toList();
        GetAllLessonsResponse expected = GetAllLessonsResponse.builder()
                .lessons(expectedLessons)
                .build();

        assertEquals(expected, actual);
        verify(lessonRepositoryMock, never()).findAllByCourseIdOrderById(anyLong());
        verify(lessonRepositoryMock).findAll(Sort.by(Sort.Direction.ASC, "id"));
    }
}