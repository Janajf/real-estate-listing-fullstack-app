package francis.janaj.spring.boot.realestatelistingserver.domain.realEstateListing.services;

import francis.janaj.spring.boot.realestatelistingserver.domain.core.exceptions.ListingException;
import francis.janaj.spring.boot.realestatelistingserver.domain.realEstateListing.models.Listing;
import francis.janaj.spring.boot.realestatelistingserver.domain.realEstateListing.repos.ListingRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ListingServiceImpl implements ListingService{

    private static Logger logger = LoggerFactory.getLogger(ListingService.class);

    private ListingRepo listingRepo;

    @Autowired
    public ListingServiceImpl(ListingRepo listingRepo){
        this.listingRepo = listingRepo;
    }
    @Override
    public Listing create(Listing listing) {
        Listing savedListing = listingRepo.save(listing);
        return savedListing;
    }

    @Override
    public Listing getListingById(Integer id) throws ListingException{
        Optional<Listing> listingOptional = listingRepo.findById(id);
        if(listingOptional.isEmpty()){
            logger.error("Listing with id {} does not exist", id);
            throw new ListingException("Listing not found");
        }
        return listingOptional.get();
    }

    @Override
    public List<Listing> getAllListings() {

        return listingRepo.findAll();
    }

    @Override
    public Listing updateListing(Integer id, Listing listing) throws ListingException {
        Optional<Listing> listingOptional = listingRepo.findById(id);
        if(listingOptional.isEmpty()){
            throw new ListingException("Listing does not exists, cannot update");
        }

        Listing savedListing = listingOptional.get();

        savedListing.setLocation(listing.getLocation());
        savedListing.setSize(listing.getSize());
        savedListing.setPrice(listing.getPrice());
        savedListing.setUser(listing.getUser());
        return listingRepo.save(savedListing);
    }

    @Override
    public Boolean deleteListing(Integer id) throws ListingException {
        Optional<Listing> listingOptional = listingRepo.findById(id);
        if(listingOptional.isEmpty()){
            throw new ListingException("Listing does not exist, cannot delete");
        }

        Listing listingToDelete = listingOptional.get();
        listingRepo.delete(listingToDelete);

        return true;
    }
}
