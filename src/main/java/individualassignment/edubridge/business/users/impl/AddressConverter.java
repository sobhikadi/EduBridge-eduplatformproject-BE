package individualassignment.edubridge.business.users.impl;

import individualassignment.edubridge.domain.address.Address;
import individualassignment.edubridge.persistence.address.entities.AddressEntity;

public class AddressConverter {
    private AddressConverter() {
    }

    public static Address convert(AddressEntity address) {
        return Address.builder()
                .id(address.getId())
                .street(address.getStreet())
                .city(address.getCity())
                .zipCode(address.getZipcode())
                .country(CountryConverter.convert(address.getCountry()))
                .build();
    }
}
