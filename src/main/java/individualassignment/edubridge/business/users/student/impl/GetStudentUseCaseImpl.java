package individualassignment.edubridge.business.users.student.impl;

import individualassignment.edubridge.business.users.exceptions.UnauthorizedDataAccessException;
import individualassignment.edubridge.business.users.impl.StudentConverter;
import individualassignment.edubridge.business.users.student.GetStudentUseCase;
import individualassignment.edubridge.domain.users.AccessToken;
import individualassignment.edubridge.domain.users.Student;
import individualassignment.edubridge.persistence.users.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;


@Service
@AllArgsConstructor
public class GetStudentUseCaseImpl implements GetStudentUseCase {

    private StudentRepository studentRepository;
    private AccessToken requestAccessToken;

    @Override
    public Optional<Student> getStudent(long studentId) {
        if (!Objects.equals(requestAccessToken.getStudentId(), studentId)) {
            throw new UnauthorizedDataAccessException();
        }
        return studentRepository.findById(studentId).map(StudentConverter::convert);
    }
}
