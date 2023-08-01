package francis.janaj.spring.boot.realestatelistingserver.domain.realEstateListing.repos;

import francis.janaj.spring.boot.realestatelistingserver.domain.realEstateListing.models.Listing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ListingRepo extends JpaRepository<Listing, Integer> {
    Optional<Listing> findListingByUserId(Integer userId);
}
