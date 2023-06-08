package individualassignment.edubridge.domain.users.responses;

import individualassignment.edubridge.domain.users.Admin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllAdminsResponse {
    private List<Admin> admins;
}
