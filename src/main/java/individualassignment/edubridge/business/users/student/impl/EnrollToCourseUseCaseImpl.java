package individualassignment.edubridge.business.users.student.impl;

import individualassignment.edubridge.business.course.exceptions.InvalidCourseIdException;
import individualassignment.edubridge.business.users.exceptions.UnauthorizedDataAccessException;
import individualassignment.edubridge.business.users.student.AddCourseToStudentUseCase;
import individualassignment.edubridge.business.users.student.exceptions.InvalidStudentException;
import individualassignment.edubridge.business.users.student.exceptions.StudentAlreadyBeenEnrolledToCourseException;
import individualassignment.edubridge.domain.users.AccessToken;
import individualassignment.edubridge.domain.users.requests.AddCourseToStudentRequest;
import individualassignment.edubridge.persistence.courses.CourseRepository;
import individualassignment.edubridge.persistence.courses.entities.CourseEntity;
import individualassignment.edubridge.persistence.users.StudentFollowedCourseRepository;
import individualassignment.edubridge.persistence.users.StudentRepository;
import individualassignment.edubridge.persistence.users.entities.StudentEntity;
import individualassignment.edubridge.persistence.users.entities.StudentFollowedCourseEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Objects;

@Service
@AllArgsConstructor
public class EnrollToCourseUseCaseImpl implements AddCourseToStudentUseCase {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final StudentFollowedCourseRepository studentFollowedCourseRepository;
    private AccessToken requestAccessToken;

    @Override
    @Transactional
    public void addCourseToStudent(AddCourseToStudentRequest request) {
        if (!Objects.equals(requestAccessToken.getStudentId(), request.getStudentId())) {
            throw new UnauthorizedDataAccessException();
        }

        StudentEntity studentEntity = studentRepository.findById(request.getStudentId()).orElseThrow(InvalidStudentException::new);
        CourseEntity courseEntity = courseRepository.findById(request.getCourseId()).orElseThrow(InvalidCourseIdException::new);
        StudentFollowedCourseEntity alreadyEnrolled =
                studentFollowedCourseRepository.findByStudentAndCourse(studentEntity, courseEntity).orElse(null);

        if(alreadyEnrolled != null) {
            throw new StudentAlreadyBeenEnrolledToCourseException();
        }

        StudentFollowedCourseEntity studentFollowedCourseEntity = StudentFollowedCourseEntity.builder()
                .course(courseEntity)
                .student(studentEntity)
                .followingDate(LocalDate.now())
                .build();

        studentFollowedCourseRepository.save(studentFollowedCourseEntity);
    }
}
