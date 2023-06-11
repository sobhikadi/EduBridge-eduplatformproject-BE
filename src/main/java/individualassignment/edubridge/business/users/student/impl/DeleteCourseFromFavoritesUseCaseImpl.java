package individualassignment.edubridge.business.users.student.impl;

import individualassignment.edubridge.business.users.student.DeleteCourseFromStudentUseCase;
import individualassignment.edubridge.business.users.student.exceptions.InvalidStudentException;
import individualassignment.edubridge.persistence.users.StudentRepository;
import individualassignment.edubridge.persistence.users.entities.StudentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteCourseFromFavoritesUseCaseImpl implements DeleteCourseFromStudentUseCase{

    private final StudentRepository studentRepository;

    @Override
    @Transactional
    public void deleteCourseFromStudent(long studentId, long courseId) {
        StudentEntity student = studentRepository.findById(studentId).orElseThrow(InvalidStudentException::new);
        student.getFavoriteCourses().removeIf(course -> course.getId() == courseId);

        studentRepository.save(student);
    }
}
