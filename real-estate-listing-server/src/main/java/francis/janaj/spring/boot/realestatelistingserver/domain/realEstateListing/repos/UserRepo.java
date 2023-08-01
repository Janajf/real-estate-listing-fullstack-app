package francis.janaj.spring.boot.realestatelistingserver.domain.realEstateListing.repos;

import francis.janaj.spring.boot.realestatelistingserver.domain.realEstateListing.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
}
