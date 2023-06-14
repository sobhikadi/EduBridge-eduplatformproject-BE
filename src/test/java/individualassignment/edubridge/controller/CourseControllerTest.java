package individualassignment.edubridge.controller;

import individualassignment.edubridge.business.course.*;
import individualassignment.edubridge.business.course.exceptions.CourseNameAlreadyExistsException;
import individualassignment.edubridge.domain.categories.Category;
import individualassignment.edubridge.domain.courses.Course;
import individualassignment.edubridge.domain.courses.CoursePublishStateEnum;
import individualassignment.edubridge.domain.courses.requests.CreateCourseRequest;
import individualassignment.edubridge.domain.courses.requests.GetAllCoursesRequest;
import individualassignment.edubridge.domain.courses.requests.UpdateCourseRequest;
import individualassignment.edubridge.domain.courses.responses.CreateCourseResponse;
import individualassignment.edubridge.domain.courses.responses.GetAllCoursesResponse;
import individualassignment.edubridge.domain.lessons.Lesson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;



    @MockBean
    private CreateCourseUseCase createCourseUseCase;

    @MockBean
    private GetAllCoursesUseCase getAllCoursesUseCase;

    @MockBean
    private GetCourseUseCase getCourseUseCase;

    @MockBean
    private DeleteCourseUseCase deleteCourseUseCase;

    @MockBean
    private UpdateCourseUseCase updateCourseUseCase;

    @MockBean
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "admin@edubridge.nl", roles = {"ADMIN", "TEACHER"})
    void createCourse_shouldReturn201_whenRequestIsValid() throws Exception {
        CreateCourseRequest request = CreateCourseRequest.builder()
                .title("Java Basics")
                .description("Learn the basics of Java")
                .provider("EduBridge")
                .publishState(CoursePublishStateEnum.PUBLISHED)
                .categoryId(1L)
                .image(null)
                .build();

        String requestJson = new ObjectMapper().writeValueAsString(request);

        MockMultipartFile imageFile =
                new MockMultipartFile("image", "image.jpg", MediaType.IMAGE_JPEG_VALUE, "some-image".getBytes());

        CreateCourseResponse expectedResponse = CreateCourseResponse.builder()
                .courseId(1L)
                .build();

        when(objectMapper.readValue(requestJson, CreateCourseRequest.class)).thenReturn(request);
        request.setImage(imageFile);
        when(createCourseUseCase.createCourse(request))
                .thenReturn(expectedResponse);


        mockMvc.perform(MockMvcRequestBuilders.multipart("/courses")
                        .file(imageFile)
                        .param("courseInfo", requestJson)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json("""
                            {"courseId":1}
                        """));


        verify(createCourseUseCase).createCourse(request);
    }

    @Test
    @WithMockUser(username = "admin@edubridge.nl", roles = {"ADMIN", "TEACHER"})
    void createCourse_shouldReturn400_whenCreationFails() throws Exception {
        CreateCourseRequest request = CreateCourseRequest.builder()
                .title("Java Basics")
                .description("Learn the basics of Java")
                .provider("EduBridge")
                .publishState(CoursePublishStateEnum.PUBLISHED)
                .categoryId(1L)
                .image(null)
                .build();

        String requestJson = new ObjectMapper().writeValueAsString(request);

        MockMultipartFile imageFile =
                new MockMultipartFile("image", "image.jpg", MediaType.IMAGE_JPEG_VALUE, "some-image".getBytes());

        when(objectMapper.readValue(requestJson, CreateCourseRequest.class)).thenReturn(request);
        request.setImage(imageFile);
        when(createCourseUseCase.createCourse(request))
                .thenThrow(new CourseNameAlreadyExistsException()); // Simulating an internal error

        mockMvc.perform(MockMvcRequestBuilders.multipart("/courses")
                        .file(imageFile)
                        .param("courseInfo", requestJson)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CourseNameAlreadyExistsException));
        verify(createCourseUseCase).createCourse(request);
    }

    @Test
    @WithMockUser(username = "admin@edubridge.nl", roles = {"ADMIN", "TEACHER"})
    void createCourse_shouldReturn400_whenRequestIsInvalid() throws Exception {
        CreateCourseRequest request = CreateCourseRequest.builder()
                .title("")  // Invalid title
                .description("Learn the basics of Java")
                .provider("EduBridge")
                .publishState(CoursePublishStateEnum.PUBLISHED)
                .categoryId(1L)
                .image(null)
                .build();

        String requestJson = new ObjectMapper().writeValueAsString(request);

        MockMultipartFile imageFile =
                new MockMultipartFile("image", "image.jpg", MediaType.IMAGE_JPEG_VALUE, "some-image".getBytes());

        when(objectMapper.readValue(requestJson, CreateCourseRequest.class)).thenReturn(request);
        request.setImage(imageFile);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/courses")
                        .file(imageFile)
                        .param("courseInfo", requestJson)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(print())
                .andExpect(status().isBadRequest());
        verify(createCourseUseCase, times(0)).createCourse(request);
    }

    @Test
    void getAllCourses_shouldReturn200AndAllCourses_WhenEmptyRequestIsProvided() throws Exception {
        GetAllCoursesRequest request = GetAllCoursesRequest.builder()
                .searchTerm("")
                .categoryId(0L)
                .build();

        Course course1 = Course.builder()
                .id(1L)
                .title("Java Basics")
                .description("Learn the basics of Java")
                .provider("EduBridge")
                .publishState(CoursePublishStateEnum.PUBLISHED)
                .category(Category.builder().id(1L).name("Category").build())
                .creationDate(LocalDate.parse("2021-07-01"))
                .publishDate(Optional.of(LocalDate.parse("2021-07-01")))
                .imageUrl("image.jpg")
                .lastModified(Optional.empty())
                .lessons(List.of(Lesson.builder().id(1L).name("Lesson 1").description("Lesson description").build()))
                .build();

        Course course2 = Course.builder()
                .id(2L)
                .title("Advanced Java")
                .description("Learn advanced Java techniques")
                .provider("EduBridge")
                .publishState(CoursePublishStateEnum.PUBLISHED)
                .category(Category.builder().id(1L).name("Category").build())
                .creationDate(LocalDate.parse("2021-07-01"))
                .publishDate(Optional.of(LocalDate.parse("2021-07-01")))
                .imageUrl("image.jpg")
                .lastModified(Optional.empty())
                .lessons(List.of(Lesson.builder().id(1L).name("Lesson 1").description("Lesson description").build()))
                .build();

        GetAllCoursesResponse expectedResponse = GetAllCoursesResponse.builder()
                .courses(List.of(course1, course2))
                .build();

        when(getAllCoursesUseCase.getAllCourses(request)).thenReturn(expectedResponse);

        mockMvc.perform(get("/courses")
                        .param("searchTerm", "")
                        .param("categoryId", "0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                            {
                                "courses":[
                                    {
                                        "id":1,
                                        "title":"Java Basics",
                                        "description":"Learn the basics of Java",
                                        "provider":"EduBridge",
                                        "publishState":"PUBLISHED",
                                        "category":{"id":1,"name":"Category"},
                                        "creationDate": "2021-07-01",
                                        "publishDate":"2021-07-01",
                                        "imageUrl":"image.jpg",
                                        "lastModified":null,
                                        "lessons":[{"id":1,"name":"Lesson 1","description":"Lesson description"}]
                                    },
                                    {
                                        "id":2,
                                        "title":"Advanced Java",
                                        "description":"Learn advanced Java techniques",
                                        "provider":"EduBridge",
                                        "publishState":"PUBLISHED",
                                        "category":{"id":1,"name":"Category"},
                                        "creationDate":"2021-07-01",
                                        "publishDate":"2021-07-01",
                                        "imageUrl":"image.jpg",
                                        "lastModified":null,
                                        "lessons":[{"id":1,"name":"Lesson 1","description":"Lesson description"}]
                                    }
                                ]
                            }
                        """));

        verify(getAllCoursesUseCase).getAllCourses(request);
    }

    @Test
    void getAllCourses_shouldReturn200AndAllCoursesOfCategory_WhenCategoryIdProvided() throws Exception {
        GetAllCoursesRequest request = GetAllCoursesRequest.builder()
                .searchTerm("")
                .categoryId(1L)
                .build();

        Course course1 = Course.builder()
                .id(1L)
                .title("Java Basics")
                .description("Learn the basics of Java")
                .provider("EduBridge")
                .publishState(CoursePublishStateEnum.PUBLISHED)
                .category(Category.builder().id(1L).name("Category").build())
                .creationDate(LocalDate.parse("2021-07-01"))
                .publishDate(Optional.of(LocalDate.parse("2021-07-01")))
                .imageUrl("image.jpg")
                .lastModified(Optional.empty())
                .lessons(List.of(Lesson.builder().id(1L).name("Lesson 1").description("Lesson description").build()))
                .build();

        GetAllCoursesResponse expectedResponse = GetAllCoursesResponse.builder()
                .courses(List.of(course1))
                .build();

        when(getAllCoursesUseCase.getAllCourses(request)).thenReturn(expectedResponse);

        mockMvc.perform(get("/courses")
                        .param("searchTerm", "")
                        .param("categoryId", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                            {
                                "courses":[
                                    {
                                        "id":1,
                                        "title":"Java Basics",
                                        "description":"Learn the basics of Java",
                                        "provider":"EduBridge",
                                        "publishState":"PUBLISHED",
                                        "category":{"id":1,"name":"Category"},
                                        "creationDate": "2021-07-01",
                                        "publishDate":"2021-07-01",
                                        "imageUrl":"image.jpg",
                                        "lastModified":null,
                                        "lessons":[{"id":1,"name":"Lesson 1","description":"Lesson description"}]
                                    }
                                    ]
                            }
                        """));

        verify(getAllCoursesUseCase).getAllCourses(request);
    }

    @Test
    void getAllCourses_shouldReturn200AndAllCoursesOfTitle_WhenTitleIsProvided() throws Exception {
        GetAllCoursesRequest request = GetAllCoursesRequest.builder()
                .searchTerm("Java")
                .categoryId(0L)
                .build();

        Course course1 = Course.builder()
                .id(1L)
                .title("Java Basics")
                .description("Learn the basics of Java")
                .provider("EduBridge")
                .publishState(CoursePublishStateEnum.PUBLISHED)
                .category(Category.builder().id(1L).name("Category").build())
                .creationDate(LocalDate.parse("2021-07-01"))
                .publishDate(Optional.of(LocalDate.parse("2021-07-01")))
                .imageUrl("image.jpg")
                .lastModified(Optional.empty())
                .lessons(List.of(Lesson.builder().id(1L).name("Lesson 1").description("Lesson description").build()))
                .build();

        Course course2 = Course.builder()
                .id(2L)
                .title("Advanced Java")
                .description("Learn advanced Java techniques")
                .provider("EduBridge")
                .publishState(CoursePublishStateEnum.PUBLISHED)
                .category(Category.builder().id(1L).name("Category").build())
                .creationDate(LocalDate.parse("2021-07-01"))
                .publishDate(Optional.of(LocalDate.parse("2021-07-01")))
                .imageUrl("image.jpg")
                .lastModified(Optional.empty())
                .lessons(List.of(Lesson.builder().id(1L).name("Lesson 1").description("Lesson description").build()))
                .build();

        GetAllCoursesResponse expectedResponse = GetAllCoursesResponse.builder()
                .courses(List.of(course1, course2))
                .build();

        when(getAllCoursesUseCase.getAllCourses(request)).thenReturn(expectedResponse);

        mockMvc.perform(get("/courses")
                        .param("searchTerm", "Java")
                        .param("categoryId", "0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                            {
                                "courses":[
                                    {
                                        "id":1,
                                        "title":"Java Basics",
                                        "description":"Learn the basics of Java",
                                        "provider":"EduBridge",
                                        "publishState":"PUBLISHED",
                                        "category":{"id":1,"name":"Category"},
                                        "creationDate": "2021-07-01",
                                        "publishDate":"2021-07-01",
                                        "imageUrl":"image.jpg",
                                        "lastModified":null,
                                        "lessons":[{"id":1,"name":"Lesson 1","description":"Lesson description"}]
                                    },
                                    {
                                        "id":2,
                                        "title":"Advanced Java",
                                        "description":"Learn advanced Java techniques",
                                        "provider":"EduBridge",
                                        "publishState":"PUBLISHED",
                                        "category":{"id":1,"name":"Category"},
                                        "creationDate": "2021-07-01",
                                        "publishDate":"2021-07-01",
                                        "imageUrl":"image.jpg",
                                        "lastModified":null,
                                        "lessons":[{"id":1,"name":"Lesson 1","description":"Lesson description"}]
                                    }
                                    ]
                            }
                        """));

        verify(getAllCoursesUseCase).getAllCourses(request);
    }

    @Test
    void getAllCourses_shouldReturn200_whenNoCoursesFound() throws Exception {
        GetAllCoursesRequest request = GetAllCoursesRequest.builder()
                .searchTerm("Python") // Assuming no courses for Python
                .categoryId(1L)
                .build();

        GetAllCoursesResponse expectedResponse = new GetAllCoursesResponse(Collections.emptyList());

        when(getAllCoursesUseCase.getAllCourses(request)).thenReturn(expectedResponse);

        mockMvc.perform(get("/courses")
                        .param("searchTerm", "Python")
                        .param("categoryId", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedResponse)));

        verify(getAllCoursesUseCase).getAllCourses(request);
    }

    @Test
    public void getCourseByTitle_ShouldReturn200WithCourse_WhenCourseExists() throws Exception {
        String title = "Java Basics";

        Course expectedCourse = Course.builder()
                .id(1L)
                .title("Java Basics")
                .description("Learn the basics of Java")
                .provider("EduBridge")
                .publishState(CoursePublishStateEnum.PUBLISHED)
                .category(Category.builder().id(1L).name("Category").build())
                .creationDate(LocalDate.parse("2021-07-01"))
                .publishDate(Optional.of(LocalDate.parse("2021-07-01")))
                .imageUrl("image.jpg")
                .lastModified(Optional.empty())
                .lessons(List.of(Lesson.builder().id(1L).name("Lesson 1").description("Lesson description").build()))
                .build();

        when(getCourseUseCase.getCourse(title)).thenReturn(expectedCourse);

        mockMvc.perform(get("/courses"+"/"+title)
                        .contentType(APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("""
                            {
                                "id":1,
                                "title":"Java Basics",
                                "description":"Learn the basics of Java",
                                "provider":"EduBridge",
                                "publishState":"PUBLISHED",
                                "category":{"id":1,"name":"Category"},
                                "creationDate": "2021-07-01",
                                "publishDate":"2021-07-01",
                                "imageUrl":"image.jpg",
                                "lastModified":null,
                                "lessons":[{"id":1,"name":"Lesson 1","description":"Lesson description"}]
                            }
                        """));

        verify(getCourseUseCase).getCourse(title);
    }

    @Test
    public void getCourseByTitleShouldReturn404WhenCourseDoesNotExist() throws Exception {
        String title = "Test Course";

        when(getCourseUseCase.getCourse(title)).thenReturn(null);

        mockMvc.perform(get("/courses"+"/"+title))
                .andDo(print())
                .andExpect(status().isNotFound());


        verify(getCourseUseCase).getCourse(title);
    }

    @Test
    @WithMockUser(username = "admin@edubridge.nl", roles = {"ADMIN"})
    public void deleteCourse_ShouldReturn204_WhenSuccessful() throws Exception {
        doNothing().when(deleteCourseUseCase).deleteCourse(1L);

        mockMvc.perform(delete("/courses/1"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(deleteCourseUseCase).deleteCourse(1L);
    }

    @Test
    @WithMockUser(username = "admin@edubridge.nl", roles = {"ADMIN"})
    public void deleteCourse_ShouldReturn409_WhenDataIntegrityViolation() throws Exception {
        doThrow(DataIntegrityViolationException.class).when(deleteCourseUseCase).deleteCourse(1L);

        mockMvc.perform(delete("/courses/" + 1L))
                .andExpect(status().isConflict());

        verify(deleteCourseUseCase).deleteCourse(1L);
    }

    @Test
    @WithMockUser(username = "admin@edubridge.nl", roles = {"ADMIN"})
    public void updateCourse_ShouldReturn200_WhenRequestIsValid() throws Exception {
        Long courseId = 1L;
        UpdateCourseRequest request = UpdateCourseRequest.builder()
                .title("Java Basics")
                .description("Learn the basics of Java")
                .provider("EduBridge")
                .publishState(CoursePublishStateEnum.PUBLISHED)
                .categoryId(1L)
                .image(null)
                .build();

        String requestJson = new ObjectMapper().writeValueAsString(request);

        MockMultipartFile imageFile =
                new MockMultipartFile("image", "image.jpg", MediaType.IMAGE_JPEG_VALUE, "some-image".getBytes());

        when(objectMapper.readValue(requestJson, UpdateCourseRequest.class)).thenReturn(request);
        request.setImage(imageFile);
        request.setId(courseId);
        doNothing().when(updateCourseUseCase).updateCourse(request);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/courses/" + courseId)
                        .file(imageFile)
                        .param("courseInfo", requestJson)
                        .with(req -> {req.setMethod("PUT"); return req;})
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());

        verify(updateCourseUseCase).updateCourse(request);
    }

    @Test
    @WithMockUser(username = "admin@edubridge.nl", roles = {"ADMIN"})
    public void updateCourse_ShouldReturn400_WhenRequestIsInvalid() throws Exception {
        Long courseId = 1L;
        UpdateCourseRequest request = UpdateCourseRequest.builder()
                .title("")
                .description("Learn the basics of Java")
                .provider("EduBridge")
                .publishState(CoursePublishStateEnum.PUBLISHED)
                .categoryId(1L)
                .image(null)
                .build();

        String requestJson = new ObjectMapper().writeValueAsString(request);

        MockMultipartFile imageFile =
                new MockMultipartFile("image", "image.jpg", MediaType.IMAGE_JPEG_VALUE, "some-image".getBytes());

        when(objectMapper.readValue(requestJson, UpdateCourseRequest.class)).thenReturn(request);
        request.setImage(imageFile);
        request.setId(courseId);
        doNothing().when(updateCourseUseCase).updateCourse(request);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/courses/" + courseId)
                        .file(imageFile)
                        .param("courseInfo", requestJson)
                        .with(req -> {req.setMethod("PUT"); return req;})
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest());

        verify(updateCourseUseCase, times(0)).updateCourse(request);
    }

}