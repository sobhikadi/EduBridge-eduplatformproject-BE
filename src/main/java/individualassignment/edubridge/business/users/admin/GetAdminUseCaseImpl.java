package individualassignment.edubridge.business.users.admin;

import individualassignment.edubridge.business.users.exceptions.UnauthorizedDataAccessException;
import individualassignment.edubridge.business.users.impl.AdminConverter;
import individualassignment.edubridge.domain.users.AccessToken;
import individualassignment.edubridge.domain.users.Admin;
import individualassignment.edubridge.persistence.users.AdminRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GetAdminUseCaseImpl implements GetAdminUseCase{
    private AdminRepository adminRepository;
    private AccessToken requestAccessToken;

    @Override
    @Transactional
    public Optional<Admin> getAdmin(long adminId) {
        if (!Objects.equals(requestAccessToken.getAdminId(), adminId)) {
            throw new UnauthorizedDataAccessException();
        }
        return adminRepository.findById(adminId).map(AdminConverter::convert);
    }
}
