package francis.janaj.spring.boot.realestatelistingserver.domain.realEstateListing.controllers;

import francis.janaj.spring.boot.realestatelistingserver.domain.core.exceptions.ListingException;
import francis.janaj.spring.boot.realestatelistingserver.domain.core.exceptions.UserException;
import francis.janaj.spring.boot.realestatelistingserver.domain.realEstateListing.models.Listing;
import francis.janaj.spring.boot.realestatelistingserver.domain.realEstateListing.services.ListingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/listings")
public class ListingController {

    private final Logger logger = LoggerFactory.getLogger(ListingController.class);

    private ListingService listingService;

    @Autowired
    public ListingController(ListingService listingService){
        this.listingService = listingService;
    }

    @PostMapping("/{id}")
    public ResponseEntity<Listing> createListing(@RequestParam Integer userId, @RequestBody Listing listing) throws UserException{
        Listing createdListing = listingService.createListing(userId, listing);
        return new ResponseEntity<>(createdListing, HttpStatus.CREATED);
    }

    @PostMapping("")
    public ResponseEntity<Listing> createListingRequest(@RequestBody Listing listing){
        Listing savedListing = listingService.create(listing);
        ResponseEntity response = new ResponseEntity(savedListing, HttpStatus.CREATED);
        return response;
    }

    @GetMapping("")
    public ResponseEntity<List<Listing>> getAllListings(){
        List<Listing> listings = listingService.getAllListings();
        return new ResponseEntity<>(listings, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getListingById(@PathVariable Integer id){
        try{
            Listing listing = listingService.getListingById(id);
            ResponseEntity<?> response = new ResponseEntity<>(listing, HttpStatus.OK);
            return response;
        } catch (ListingException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateListing(@PathVariable Integer id, @RequestBody Listing listing){
        try{
            Listing updateListing = listingService.updateListing(id,listing);
            ResponseEntity response = new ResponseEntity(updateListing,HttpStatus.OK);
            return response;
        } catch(ListingException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteListing(@PathVariable Integer id){
        try{
            listingService.deleteListing(id);
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .build();
        } catch(ListingException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

}
