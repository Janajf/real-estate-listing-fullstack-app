package francis.janaj.spring.boot.realestatelistingserver.realestatelisting.models;

import francis.janaj.spring.boot.realestatelistingserver.domain.realEstateListing.models.Listing;
import francis.janaj.spring.boot.realestatelistingserver.domain.realEstateListing.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private User user1;
    private User user2;
    private User emptyUser1;
    private User emptyUser2;

    @BeforeEach
    public void setUp(){
        emptyUser1 = new User();
        emptyUser2 = new User();

        Listing listing1 = new Listing( "Wilmington, DE", "Large", "400000", 1);
        listing1.setId(1);

        Listing listing2 = new Listing( "Wilmington, DE", "Med", "350000",2);
        listing2.setId(2);
        List<Listing> listings1 = new ArrayList<>();
        List<Listing> listings2 = new ArrayList<>();

        listings1.add(listing1);
        listings2.add(listing2);

        user1 = new User("Tariq", "Hook", "thook@me.com","1234",listings1);
        user1.setId(1);

        user2 = new User("Janaj", "Francis", "jfrancis@me.com","5678",listings2);
        user2.setId(2);

    }

    @Test public void testEmptyEquals() throws Exception{
        assertTrue(
                emptyUser1.equals(emptyUser2),
                "Both empty Users instances should be equal"
        );
    }

    @Test
    public void testContentNotEquals() throws Exception{
        assertNotEquals(
                user1,user2,
                "User 1 and User 2 should not be equal"
        );
    }

    @Test
    public void testNotEquals() throws Exception{
        assertNotEquals(emptyUser1, user1, "The User instances should not be equal");
    }

    @Test
    public void testEmptyHashCode() throws Exception{
        assertEquals(
                emptyUser1.hashCode(),
                emptyUser2.hashCode(),
                "Both empty User instances should have the same hashCode"
        );
    }

    @Test
    public void testContentHashCode() throws Exception{
        assertNotEquals(
                user1.hashCode(),
                user2.hashCode(),
                "Both non-empty User instances should not have the same hashcode"
        );
    }

    @Test
    public void testHashCode() throws Exception{
        assertNotEquals(emptyUser1.hashCode(),
                user2.hashCode(),
                "The User instances should not have the same hashcode");

    }

    @Test
    public void testEmptyToString() throws Exception{
        assertEquals(
                emptyUser1.toString(),
                emptyUser2.toString(),
                "Both empty User instances should have the same toString"
        );
    }

    @Test public void testContentToString() throws Exception{
        assertNotEquals(user1.toString(),
                user2.toString(),
                "Both User instances should not have the same toString");
    }

    @Test
    public void testNotToString() throws Exception{
        assertNotEquals(
                emptyUser1.toString(),
                user2.toString(),
                "Both User instances should not have the same toString");
    }
}
