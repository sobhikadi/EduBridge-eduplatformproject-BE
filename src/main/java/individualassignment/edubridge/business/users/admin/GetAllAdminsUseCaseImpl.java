package individualassignment.edubridge.business.users.admin;

import individualassignment.edubridge.business.users.impl.AdminConverter;
import individualassignment.edubridge.domain.users.Admin;
import individualassignment.edubridge.domain.users.responses.GetAllAdminsResponse;
import individualassignment.edubridge.persistence.users.AdminRepository;
import individualassignment.edubridge.persistence.users.entities.AdminEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllAdminsUseCaseImpl implements GetAllAdminsUseCase{

    private final AdminRepository adminRepository;
    @Override
    public GetAllAdminsResponse getAllAdmins() {
        List<AdminEntity> results = adminRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));

        final GetAllAdminsResponse response = new GetAllAdminsResponse();
        List<Admin> admins = results
                .stream()
                .map(AdminConverter::convert)
                .toList();

        response.setAdmins(admins);

        return response;
    }
}
