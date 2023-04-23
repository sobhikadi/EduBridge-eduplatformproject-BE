package individualassignment.edubridge.persistence.address;

import individualassignment.edubridge.persistence.address.entities.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<CountryEntity, Long> {
}
