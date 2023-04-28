package individualassignment.edubridge.business.users.student;

import individualassignment.edubridge.domain.users.requests.GetAllStudentsRequest;
import individualassignment.edubridge.domain.users.responses.GetAllStudentsResponse;

public interface GetAllStudentsUseCase {
    GetAllStudentsResponse getAllStudents(GetAllStudentsRequest request);
}
