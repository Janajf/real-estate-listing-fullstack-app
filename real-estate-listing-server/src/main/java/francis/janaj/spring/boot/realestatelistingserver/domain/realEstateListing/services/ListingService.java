package francis.janaj.spring.boot.realestatelistingserver.domain.realEstateListing.services;

import francis.janaj.spring.boot.realestatelistingserver.domain.core.exceptions.ListingException;
import francis.janaj.spring.boot.realestatelistingserver.domain.core.exceptions.UserException;
import francis.janaj.spring.boot.realestatelistingserver.domain.realEstateListing.models.Listing;

import java.util.List;

public interface ListingService {
    Listing create(Listing listing);
    Listing createListing(Integer userId, Listing listing) throws UserException;

    Listing getListingById(Integer id) throws ListingException;

    List<Listing> getAllListings();

    Listing updateListing(Integer id, Listing listing) throws ListingException;

    Boolean deleteListing(Integer id) throws ListingException;
}
