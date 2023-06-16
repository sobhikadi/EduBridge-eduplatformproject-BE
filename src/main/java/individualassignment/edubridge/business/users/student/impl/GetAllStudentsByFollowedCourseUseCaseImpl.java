package individualassignment.edubridge.business.users.student.impl;

import individualassignment.edubridge.business.course.exceptions.InvalidCourseIdException;
import individualassignment.edubridge.business.users.impl.StudentConverter;
import individualassignment.edubridge.business.users.student.GetAllStudentsByFollowedCourseUseCase;
import individualassignment.edubridge.domain.users.Student;
import individualassignment.edubridge.domain.users.responses.GetAllStudentsResponse;
import individualassignment.edubridge.persistence.users.StudentFollowedCourseRepository;
import individualassignment.edubridge.persistence.users.entities.StudentFollowedCourseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllStudentsByFollowedCourseUseCaseImpl implements GetAllStudentsByFollowedCourseUseCase {

    private final StudentFollowedCourseRepository studentFollowedCourseRepository;

    @Override
    @Transactional
    public GetAllStudentsResponse getAllStudentsByFollowedCourse(long courseId) {
        List<StudentFollowedCourseEntity> results;
        if (courseId > 0) {
            results = studentFollowedCourseRepository.findAllByCourse_Id(courseId);
        } else {
            throw new InvalidCourseIdException();
        }

        final GetAllStudentsResponse response = new GetAllStudentsResponse();
        List<Student> students = results
                .stream()
                .map(StudentFollowedCourseEntity::getStudent)
                .map(StudentConverter::convert)
                .toList();

        response.setStudents(students);

        return response;
    }
}
