package individualassignment.edubridge.business.users.student.impl;

import individualassignment.edubridge.business.course.exceptions.InvalidCourseIdException;
import individualassignment.edubridge.business.users.student.DeleteCourseFromStudentUseCase;
import individualassignment.edubridge.business.users.student.exceptions.InvalidStudentException;
import individualassignment.edubridge.persistence.courses.CourseRepository;
import individualassignment.edubridge.persistence.courses.entities.CourseEntity;
import individualassignment.edubridge.persistence.users.StudentFollowedCourseRepository;
import individualassignment.edubridge.persistence.users.StudentRepository;
import individualassignment.edubridge.persistence.users.entities.StudentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class DisenrollFromCourseUseCaseImpl implements DeleteCourseFromStudentUseCase {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final StudentFollowedCourseRepository studentFollowedCourseRepository;

    @Override
    @Transactional
    public void deleteCourseFromStudent(long studentId, long courseId) {

        StudentEntity student = studentRepository.findById(studentId).orElseThrow(InvalidStudentException::new);
        CourseEntity course = courseRepository.findById(courseId).orElseThrow(InvalidCourseIdException::new);

        studentFollowedCourseRepository.deleteByStudentAndCourse(student, course);
    }
}
