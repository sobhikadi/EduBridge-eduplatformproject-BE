package individualassignment.edubridge.business.users.student;

import individualassignment.edubridge.domain.users.requests.CreateStudentRequest;
import individualassignment.edubridge.domain.users.responses.CreateStudentResponse;

public interface CreateStudentUseCase {
    CreateStudentResponse createStudent(CreateStudentRequest request);
}
