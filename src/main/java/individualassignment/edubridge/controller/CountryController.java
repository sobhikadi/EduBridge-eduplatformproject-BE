package individualassignment.edubridge.controller;

import individualassignment.edubridge.business.users.GetAllCountriesUseCase;
import individualassignment.edubridge.domain.address.responses.GetAllCountriesResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/countries")
@AllArgsConstructor
public class CountryController {
    private final GetAllCountriesUseCase getAllCountriesUseCase;

    @GetMapping()
    public ResponseEntity<GetAllCountriesResponse> getAllCountries(){
        GetAllCountriesResponse response = getAllCountriesUseCase.getAllCountries();
        return ResponseEntity.ok(response);
    }

}
