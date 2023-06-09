package individualassignment.edubridge.business.users.student.impl;

import individualassignment.edubridge.business.course.exceptions.InvalidCourseIdException;
import individualassignment.edubridge.business.users.impl.StudentConverter;
import individualassignment.edubridge.business.users.student.GetAllStudentsByFollowedCourseUseCase;
import individualassignment.edubridge.domain.users.Student;
import individualassignment.edubridge.domain.users.responses.GetAllStudentsResponse;
import individualassignment.edubridge.persistence.users.StudentRepository;
import individualassignment.edubridge.persistence.users.entities.StudentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllStudentsByFollowedCourseUseCaseImpl implements GetAllStudentsByFollowedCourseUseCase {

    private final StudentRepository studentRepository;

    @Override
    @Transactional
    public GetAllStudentsResponse getAllStudentsByFollowedCourse(long courseId) {
        List<StudentEntity> results;
        if (courseId > 0) {
            results = studentRepository.findStudentsByCourseFollowedCourseId(courseId);
        } else {
            throw new InvalidCourseIdException();
        }

        final GetAllStudentsResponse response = new GetAllStudentsResponse();
        List<Student> students = results
                .stream()
                .map(StudentConverter::convert)
                .toList();

        response.setStudents(students);

        return response;
    }
}
