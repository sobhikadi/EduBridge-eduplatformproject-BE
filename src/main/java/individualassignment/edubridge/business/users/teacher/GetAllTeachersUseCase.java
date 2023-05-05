package individualassignment.edubridge.business.users.teacher;

import individualassignment.edubridge.domain.users.requests.GetAllTeachersRequest;
import individualassignment.edubridge.domain.users.responses.GetAllTeachersResponse;

public interface GetAllTeachersUseCase {
    GetAllTeachersResponse getAllTeachers(GetAllTeachersRequest request);
}
