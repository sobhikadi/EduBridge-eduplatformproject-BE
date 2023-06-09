package individualassignment.edubridge.business.users.student;

import individualassignment.edubridge.domain.users.responses.GetAllStudentsResponse;

public interface GetAllStudentsByFollowedCourseUseCase {
    GetAllStudentsResponse getAllStudentsByFollowedCourse(long courseId);
}
