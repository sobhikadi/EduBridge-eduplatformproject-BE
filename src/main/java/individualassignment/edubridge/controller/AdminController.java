package individualassignment.edubridge.controller;

import individualassignment.edubridge.business.users.admin.GetAdminUseCase;
import individualassignment.edubridge.business.users.admin.GetAllAdminsUseCase;
import individualassignment.edubridge.configuration.security.isauthenticated.IsAuthenticated;
import individualassignment.edubridge.domain.users.Admin;
import individualassignment.edubridge.domain.users.responses.GetAllAdminsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.Optional;

@RestController
@RequestMapping("/admins")
@RequiredArgsConstructor
public class AdminController {

    private final GetAdminUseCase getAdminUseCase;
    private final GetAllAdminsUseCase getAllAdminsUseCase;

    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN"})
    @GetMapping("{id}")
    public ResponseEntity<Admin> getAdmin(@PathVariable(value = "id") final long id) {
        final Optional<Admin> adminOptional = getAdminUseCase.getAdmin(id);
        return adminOptional.map(admin -> ResponseEntity.ok().body(admin))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN", "ROLE_TEACHER"})
    @GetMapping
    public ResponseEntity<GetAllAdminsResponse> getAdmins() {
        final GetAllAdminsResponse response = getAllAdminsUseCase.getAllAdmins();
        return ResponseEntity.ok().body(response);
    }
}
