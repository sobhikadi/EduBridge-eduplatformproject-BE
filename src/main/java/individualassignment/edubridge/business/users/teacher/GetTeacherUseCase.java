package individualassignment.edubridge.business.users.teacher;

import individualassignment.edubridge.domain.users.Teacher;

import java.util.Optional;

public interface GetTeacherUseCase {
    Optional<Teacher> getTeacher(long teacherId);
}
