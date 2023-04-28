package individualassignment.edubridge.business.users.impl;

import individualassignment.edubridge.domain.address.Country;
import individualassignment.edubridge.persistence.address.entities.CountryEntity;

public class CountryConverter {
    private CountryConverter() {
    }

    public static Country convert(CountryEntity country) {
        return Country.builder()
                .id(country.getId())
                .code(country.getCode())
                .name(country.getName())
                .build();
    }
}
