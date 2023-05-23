package individualassignment.edubridge.business.users.impl;

import individualassignment.edubridge.business.users.GetAllCountriesUseCase;
import individualassignment.edubridge.domain.address.Country;
import individualassignment.edubridge.domain.address.responses.GetAllCountriesResponse;
import individualassignment.edubridge.persistence.address.CountryRepository;
import individualassignment.edubridge.persistence.address.entities.CountryEntity;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetAllCountriesUseCaseImpl implements GetAllCountriesUseCase {

    private final CountryRepository countryRepository;

    @Override
    public GetAllCountriesResponse getAllCountries() {
        List<CountryEntity> result = countryRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));

        final GetAllCountriesResponse response = new GetAllCountriesResponse();
        List<Country> countries = result
                .stream()
                .map(CountryConverter::convert)
                .toList();
        response.setCountries(countries);
        return response;
    }
}
