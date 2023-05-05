package individualassignment.edubridge.business.users.teacher;

import individualassignment.edubridge.domain.users.requests.CreateTeacherRequest;
import individualassignment.edubridge.domain.users.responses.CreateTeacherResponse;

public interface CreateTeacherUseCase {
    CreateTeacherResponse createTeacher(CreateTeacherRequest request);
}
