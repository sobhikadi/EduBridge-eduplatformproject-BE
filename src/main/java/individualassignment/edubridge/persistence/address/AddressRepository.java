package individualassignment.edubridge.persistence.address;

import individualassignment.edubridge.persistence.address.entities.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
}
