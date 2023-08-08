package francis.janaj.spring.boot.realestatelistingserver.realestatelisting.services;

import francis.janaj.spring.boot.realestatelistingserver.domain.core.exceptions.ListingException;
import francis.janaj.spring.boot.realestatelistingserver.domain.core.exceptions.UserException;
import francis.janaj.spring.boot.realestatelistingserver.domain.realEstateListing.models.Listing;
import francis.janaj.spring.boot.realestatelistingserver.domain.realEstateListing.models.User;
import francis.janaj.spring.boot.realestatelistingserver.domain.realEstateListing.repos.ListingRepo;
import francis.janaj.spring.boot.realestatelistingserver.domain.realEstateListing.repos.UserRepo;
import francis.janaj.spring.boot.realestatelistingserver.domain.realEstateListing.services.ListingService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ListingServiceImplTest {
    @MockBean
    private ListingRepo mockListingRepo;

    @Autowired
    private ListingService listingService;

    private Listing inputListing;
    private Listing mockResponseListing1;
    private Listing mockResponseListing2;

    @BeforeEach
    public void setUp(){
        List<Listing> listings1 = new ArrayList<>();
        List<Listing> listings2 = new ArrayList<>();

        User user1 = new User("Tariq", "Hook", "thook@me.com","1234",listings1);
        user1.setId(1);

        User user2 = new User("Janaj", "Francis", "jfrancis@me.com","5678",listings2);
        user2.setId(2);

        inputListing = new Listing("Wilmington, DE", "Med", "350000", 1);

        mockResponseListing1 = new Listing("Wilmington, DE", "Med", "350000", 1);
        mockResponseListing1.setId(1);
        mockResponseListing2 = new Listing("Dover, DE", "Large", "550000", 2);
        mockResponseListing2.setId(2);

    }


    @Test
    @DisplayName("Listing Service: Create Listing - Success")
    public void createListingTestSuccess(){
        // return mockResponseListing1 when the save() method is called from mockListingRepo
        BDDMockito.doReturn(mockResponseListing1).when(mockListingRepo).save(ArgumentMatchers.any());
        // call the create() method passing the inputListing ... assign the value of the result to returnedListing
        Listing returnedListing = listingService.create(inputListing);
        // make sure returnedListing is not null
        Assertions.assertNotNull(returnedListing, "Listing should not be null");
        // make sure the id value is 1
        Assertions.assertEquals(returnedListing.getId(), 1);
    }

    @Test
    @DisplayName("Listing Service: Get Listing by Id - Success")
    public void getListingByIdTestSuccess() throws ListingException{
        // return an Optional of mockResponseListing1 when the findById() method is called from mockListingRepo
        BDDMockito.doReturn(Optional.of(mockResponseListing1)).when(mockListingRepo).findById(1);
        // call the getListingById() method passing the id value of 1... assign the resulting value to foundListing
        Listing foundListing = listingService.getListingById(1);
        // make sure mockResponseListing1 and foundListing are equal
        Assertions.assertEquals(mockResponseListing1.toString(),foundListing.toString());
    }

    @Test
    @DisplayName("Listing Service: Get Listing By Id - Success")
    public void getListingByIdTestFailed(){
        // return an empty Optional when the findById() method is called from mockListingRepo
        BDDMockito.doReturn(Optional.empty()).when(mockListingRepo).findById(1);
        // make sure the listing exception is thrown then the getListingById() is called from the listingService
        Assertions.assertThrows(ListingException.class, () -> {
            listingService.getListingById(1);
        });
    }

    @Test
    @DisplayName("Listing Service: Get All Listings - Success")
    public void getAllListingsTestSuccess(){
        List<Listing> listings = new ArrayList<>();

        listings.add(mockResponseListing1);
        listings.add(mockResponseListing2);

        // return listings when the findAll() method is call from mockListingRepo
        BDDMockito.doReturn(listings).when(mockListingRepo).findAll();

        // call the getAllListings() method ... assign the result to responseListings
        List<Listing> responseListings = listingService.getAllListings();

        // make sure listings and responseListings are equal
        Assertions.assertIterableEquals(listings, responseListings);
    }

    @Test
    @DisplayName("Listing Service: Update Listing - Success")
    public void updateListingTestSuccess() throws ListingException{
        List<Listing> listings1 = new ArrayList<>();
        User user1 = new User("Tariq", "Hook", "thook@me.com","1234",listings1);
        Listing expectedListingUpdate = new Listing("After Update Listing", "Large", "600000",1 );

        // return an Optional of mockResponseListing1 when the findById() method is called from mockListingRepo
        BDDMockito.doReturn(Optional.of(mockResponseListing1)).when(mockListingRepo).findById(1);
        // return expectedListingUpdate when the save() method is called from mockListingRepo
        BDDMockito.doReturn(expectedListingUpdate).when(mockListingRepo).save(ArgumentMatchers.any());

        // call the updateListing() method passing 1 as the id and expectedListingUpdate ... assign the result to actualListing
        Listing actualListing = listingService.updateListing(1, expectedListingUpdate);
        // make sure expectedListingUpdate and actualListing are equal
        Assertions.assertEquals(expectedListingUpdate.toString(), actualListing.toString());
    }

    @Test
    @DisplayName("Listing Service: Update Listing -Fail")
    public void updateListingTestFail(){
        List<Listing> listings1 = new ArrayList<>();
        User user1 = new User("Tariq", "Hook", "thook@me.com","1234",listings1);
        Listing expectedListingUpdate = new Listing("After Update Listing", "Large", "600000",1 );

        // return an empty Optional when the findById() is called from mockListingRepo
        BDDMockito.doReturn(Optional.empty()).when(mockListingRepo).findById(1);
        // make sure a listing exception is thrown when the updateListing() is called from listingService
        Assertions.assertThrows(ListingException.class, ()->{
            listingService.updateListing(1, expectedListingUpdate);
        });
    }

    @Test
    @DisplayName("Listing Service: Delete Listing - Success")
    public void deleteListingTestSuccess() throws ListingException{
        // return an Optional of mockResponseListing1 when the findById() method is called from mockListingRepo
        BDDMockito.doReturn(Optional.of(mockResponseListing1)).when(mockListingRepo).findById(1);
        // call the deleteListing() method from listingService ... assign the result value to actualResponse
        Boolean actualResponse = listingService.deleteListing(1);
        Assertions.assertTrue(actualResponse);
    }

    @Test
    @DisplayName("Listing Service: Delete Listing - Fail")
    public void deleteListingTestFail(){
        // return an empty Optional when the findById() is called from mockListingRepo
        BDDMockito.doReturn(Optional.empty()).when(mockListingRepo).findById(1);
        // make sure a listing exception is thrown when the deleteListing() is called from listingService
        Assertions.assertThrows(ListingException.class, () ->{
            listingService.deleteListing(1);
        });
    }

}
