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
    @MockBean
    private UserRepo mockUserRepo;

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

        inputListing = new Listing("Wilmington, DE", "Med", "350000", user1);

        mockResponseListing1 = new Listing("Wilmington, DE", "Med", "350000", user1);
        mockResponseListing1.setId(1);
        mockResponseListing2 = new Listing("Dover, DE", "Large", "550000", user2);
        mockResponseListing2.setId(2);

    }

    @Test
    @DisplayName("Listing Service: Create Listing with User - Success")
    public void createListingWithUserTestSuccess() throws UserException {
        List<Listing> listings = new ArrayList<>();
        Integer userId = 1;
        User user = new User(userId, "Janaj", "Francis", "jfrancis@me.com","5678",listings);
        BDDMockito.doReturn(Optional.of(user)).when(mockUserRepo).findById(userId);
        BDDMockito.doReturn(mockResponseListing1).when(mockListingRepo).save(ArgumentMatchers.any());
        Listing returnedListing = listingService.createListing(userId, inputListing);

        Assertions.assertNotNull(returnedListing,"Listing should not be null");


    }

    @Test
    @DisplayName("Listing Service: Create Listing - Success")
    public void createListingTestSuccess(){
        BDDMockito.doReturn(mockResponseListing1).when(mockListingRepo).save(ArgumentMatchers.any());
        Listing returnedListing = listingService.create(inputListing);
        Assertions.assertNotNull(returnedListing, "Listing should not be null");
        Assertions.assertEquals(returnedListing.getId(), 1);
    }

    @Test
    @DisplayName("Listing Service: Get Listing by Id - Success")
    public void getListingByIdTestSuccess() throws ListingException{
        BDDMockito.doReturn(Optional.of(mockResponseListing1)).when(mockListingRepo).findById(1);
        Listing foundListing = listingService.getListingById(1);
        Assertions.assertEquals(mockResponseListing1.toString(),foundListing.toString());
    }

    @Test
    @DisplayName("Listing Service: Get Listing By Id - Success")
    public void getListingByIdTestFailed(){
        BDDMockito.doReturn(Optional.empty()).when(mockListingRepo).findById(1);
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

        BDDMockito.doReturn(listings).when(mockListingRepo).findAll();

        List<Listing> responseListings = listingService.getAllListings();
        Assertions.assertIterableEquals(listings, responseListings);
    }

    @Test
    @DisplayName("Listing Service: Update Listing - Success")
    public void updateListingTestSuccess() throws ListingException{
        List<Listing> listings1 = new ArrayList<>();
        User user1 = new User("Tariq", "Hook", "thook@me.com","1234",listings1);
        Listing expectedListingUpdate = new Listing("After Update Listing", "Large", "600000",user1 );

        BDDMockito.doReturn(Optional.of(mockResponseListing1)).when(mockListingRepo).findById(1);
        BDDMockito.doReturn(expectedListingUpdate).when(mockListingRepo).save(ArgumentMatchers.any());

        Listing actualListing = listingService.updateListing(1, expectedListingUpdate);
        Assertions.assertEquals(expectedListingUpdate.toString(), actualListing.toString());
    }

    @Test
    @DisplayName("Listing Service: Update Listing -Fail")
    public void updateListingTestFail(){
        List<Listing> listings1 = new ArrayList<>();
        User user1 = new User("Tariq", "Hook", "thook@me.com","1234",listings1);
        Listing expectedListingUpdate = new Listing("After Update Listing", "Large", "600000",user1 );

        BDDMockito.doReturn(Optional.empty()).when(mockListingRepo).findById(1);
        Assertions.assertThrows(ListingException.class, ()->{
            listingService.updateListing(1, expectedListingUpdate);
        });
    }

    @Test
    @DisplayName("Listing Service: Delete Listing - Success")
    public void deleteListingTestSuccess() throws ListingException{
        BDDMockito.doReturn(Optional.of(mockResponseListing1)).when(mockListingRepo).findById(1);
        Boolean actualResponse = listingService.deleteListing(1);
        Assertions.assertTrue(actualResponse);
    }

    @Test
    @DisplayName("Listing Service: Delete Listing - Fail")
    public void deleteListingTestFail(){
        BDDMockito.doReturn(Optional.empty()).when(mockListingRepo).findById(1);
        Assertions.assertThrows(ListingException.class, () ->{
            listingService.deleteListing(1);
        });
    }

}
