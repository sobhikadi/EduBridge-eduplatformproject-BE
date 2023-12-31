package individualassignment.edubridge.business.users.student.impl;

import individualassignment.edubridge.business.users.student.DeleteStudentUseCase;
import individualassignment.edubridge.business.users.student.exceptions.InvalidStudentException;
import individualassignment.edubridge.persistence.users.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteStudentUseCaseImpl implements DeleteStudentUseCase {

    private final StudentRepository studentRepository;

    @Override
    public void deleteStudent(long studentId) {
        if(!studentRepository.existsById(studentId))
            throw new InvalidStudentException();
        studentRepository.deleteById(studentId);
    }
}
