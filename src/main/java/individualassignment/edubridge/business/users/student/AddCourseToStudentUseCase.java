package individualassignment.edubridge.business.users.student;

import individualassignment.edubridge.domain.users.requests.AddCourseToStudentRequest;

public interface AddCourseToStudentUseCase {
    void addCourseToStudent(AddCourseToStudentRequest request);
}
