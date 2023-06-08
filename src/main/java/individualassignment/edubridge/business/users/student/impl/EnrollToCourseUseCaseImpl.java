package individualassignment.edubridge.business.users.student.impl;

import individualassignment.edubridge.business.users.exceptions.UnauthorizedDataAccessException;
import individualassignment.edubridge.business.users.student.AddCourseToStudentUseCase;
import individualassignment.edubridge.business.users.student.exceptions.StudentAlreadyBeenEnrolledToCourseException;
import individualassignment.edubridge.domain.users.AccessToken;
import individualassignment.edubridge.domain.users.requests.AddCourseToStudentRequest;
import individualassignment.edubridge.persistence.courses.CourseRepository;
import individualassignment.edubridge.persistence.courses.entities.CourseEntity;
import individualassignment.edubridge.persistence.users.StudentRepository;
import individualassignment.edubridge.persistence.users.entities.StudentEntity;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@AllArgsConstructor
public class EnrollToCourseUseCaseImpl implements AddCourseToStudentUseCase {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private AccessToken requestAccessToken;
    @Override
    @Transactional
    public void addCourseToStudent(AddCourseToStudentRequest request) {
        if (!Objects.equals(requestAccessToken.getStudentId(), request.getStudentId())) {
            throw new UnauthorizedDataAccessException();
        }

        StudentEntity studentEntity = studentRepository.findById(request.getStudentId()).orElseThrow();
        CourseEntity courseEntity = courseRepository.findById(request.getCourseId()).orElseThrow();

        if(studentEntity.getFollowedCourses().contains(courseEntity)) {
            throw new StudentAlreadyBeenEnrolledToCourseException();
        }

        studentEntity.getFollowedCourses().add(courseEntity);
        studentRepository.save(studentEntity);
    }
}
