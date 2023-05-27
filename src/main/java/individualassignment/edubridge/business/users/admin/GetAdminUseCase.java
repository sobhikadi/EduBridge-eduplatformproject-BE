package individualassignment.edubridge.business.users.admin;

import individualassignment.edubridge.domain.users.Admin;
import individualassignment.edubridge.domain.users.Student;

import java.util.Optional;

public interface GetAdminUseCase {
    Optional<Admin> getAdmin(long adminId);
}
