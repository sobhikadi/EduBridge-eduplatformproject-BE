package individualassignment.edubridge.business.users.impl;

import individualassignment.edubridge.business.users.CountryIdValidator;
import individualassignment.edubridge.business.users.exceptions.InvalidCountryException;
import individualassignment.edubridge.persistence.address.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CountryIdValidatorImpl implements CountryIdValidator {

    private final CountryRepository countryRepository;

    @Override
    public void validateId(Long countryId) throws InvalidCountryException {
        if (countryId == null || !countryRepository.existsById(countryId)) {
            throw new InvalidCountryException();
        }
    }
}
