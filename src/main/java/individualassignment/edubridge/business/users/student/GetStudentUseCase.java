package individualassignment.edubridge.business.users.student;

import individualassignment.edubridge.domain.users.Student;

import java.util.Optional;

public interface GetStudentUseCase {
    Optional<Student> getStudent(long studentId);
}
