package francis.janaj.spring.boot.realestatelistingserver.realestatelisting.models;

import francis.janaj.spring.boot.realestatelistingserver.domain.realEstateListing.models.Listing;
import francis.janaj.spring.boot.realestatelistingserver.domain.realEstateListing.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ListingTest {
    private Listing listing1;
    private Listing listing2;
    private Listing emptyListing1;
    private Listing emptyListing2;

    @BeforeEach
    public void setUp(){
        emptyListing1 = new Listing();
        emptyListing2 = new Listing();
        List<Listing> listings = new ArrayList<>();

        User user = new User( "firstName", "lastName", "email@me.com", "password",listings);

        listing1 = new Listing( "Location", "Large", "400000", 1);
        listing1.setId(1);

        listing2 = new Listing( "New Location", "Med", "350000",1);
        listing2.setId(2);
    }

    @Test
    public void testEmptyEquals() throws Exception {

        assertTrue(
                emptyListing1.equals(emptyListing2),
                "Both empty Listing instances should be equal");
    }

    @Test
    public void testContentNotEquals() throws Exception {

        assertFalse(
                listing1.equals(listing2),
                "Both Listing instances should be not equal");
    }

    @Test
    public void testNotEquals() throws Exception {

        assertFalse(
                emptyListing1.equals(listing2),
                "The Listing instances should not be equal");
    }
    @Test
    public void testEmptyHashCode() throws Exception{
        assertEquals(
                emptyListing1.hashCode(),
                emptyListing2.hashCode(),
                "Both empty Listing instances should have the same hashCode"
        );
    }

    @Test
    public void testContentHashCode() throws Exception{
        assertNotEquals(
                listing1.hashCode(),
                listing2.hashCode(),
                "Both non-empty Listing instances should not have the same hashcode"
        );
    }

    @Test
    public void testHashCode() throws Exception{
        assertNotEquals(emptyListing1.hashCode(),
                listing2.hashCode(),
                "The Listing instances should not have the same hashcode");

    }

    @Test
    public void testEmptyToString() throws Exception{
        assertEquals(
                emptyListing1.toString(),
                emptyListing2.toString(),
                "Both empty Listing instances should have the same toString"
        );
    }

    @Test public void testContentToString() throws Exception{
        assertNotEquals(listing1.toString(),
                listing2.toString(),
                "Both Listing instances should not have the same toString");
    }

    @Test
    public void testNotToString() throws Exception{
        assertNotEquals(
                emptyListing1.toString(),
                listing2.toString(),
                "Both Listing instances should not have the same toString");
    }
}
