package individualassignment.edubridge.controller;

import individualassignment.edubridge.business.users.admin.GetAdminUseCase;
import individualassignment.edubridge.configuration.security.isauthenticated.IsAuthenticated;
import individualassignment.edubridge.domain.users.Admin;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.Optional;

@RestController
@RequestMapping("/admins")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    private final GetAdminUseCase getAdminUseCase;

    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN"})
    @GetMapping("{id}")
    public ResponseEntity<Admin> getAdmin(@PathVariable(value = "id") final long id) {
        final Optional<Admin> adminOptional = getAdminUseCase.getAdmin(id);
        return adminOptional.map(admin -> ResponseEntity.ok().body(admin))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
