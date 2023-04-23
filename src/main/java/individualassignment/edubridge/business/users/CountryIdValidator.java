package individualassignment.edubridge.business.users;

import individualassignment.edubridge.business.users.exceptions.InvalidCountryException;

public interface CountryIdValidator {
    void validateId(Long countryId) throws InvalidCountryException;
}
