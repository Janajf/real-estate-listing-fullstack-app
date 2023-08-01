package francis.janaj.spring.boot.realestatelistingserver.realestatelisting.services;

import francis.janaj.spring.boot.realestatelistingserver.domain.core.exceptions.UserException;
import francis.janaj.spring.boot.realestatelistingserver.domain.realEstateListing.models.Listing;
import francis.janaj.spring.boot.realestatelistingserver.domain.realEstateListing.models.User;
import francis.janaj.spring.boot.realestatelistingserver.domain.realEstateListing.repos.UserRepo;
import francis.janaj.spring.boot.realestatelistingserver.domain.realEstateListing.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
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
public class UserServiceImplTest {

    @MockBean
    private UserRepo mockUserRepo;

    @Autowired
    private UserService userService;

    private User inputUser;
    private User mockResponseUser1;
    private User mockResponseUser2;

    @BeforeEach
    public void setUp(){
        List<Listing> listings1 = new ArrayList<>();
        List<Listing> listings2 = new ArrayList<>();

        Listing listing1 = new Listing( "Wilmington, DE", "Large", "400000", inputUser);
        Listing listing2 = new Listing( "Wilmington, DE", "Med", "350000",inputUser);

        inputUser = new User( "Test User firstname", "Test User firstname", "test@me.com","1234",listings1);

        listings1.add(listing1);
        listings2.add(listing2);

        mockResponseUser1 = new User( "Test User firstname 01", "Test User firstname 01", "test@me.com", "1234",listings1);
        mockResponseUser1.setId(1);
        mockResponseUser2 = new User( "Test User firstname 02", "Test User firstname 02", "test@me2.com", "5678",listings2);
        mockResponseUser2.setId(2);
    }

    @Test
    @DisplayName("User Service: Create User - Success")
    public void createUserTestSuccess(){
        BDDMockito.doReturn(mockResponseUser1).when(mockUserRepo).save(ArgumentMatchers.any());
        User returnedUser = userService.create(inputUser);
        Assertions.assertNotNull(returnedUser, "User should not be null");
        Assertions.assertEquals(returnedUser.getId(), 1);
    }

    @Test
    @DisplayName("Widget Service: Get User by Id - Success")
    public void getUserByIdTestSuccess() throws UserException{
        BDDMockito.doReturn(Optional.of(mockResponseUser1)).when(mockUserRepo).findById(1);
        User foundUser = userService.getUserById(1);
        Assertions.assertEquals(mockResponseUser1.toString(), foundUser.toString());
    }

    @Test
    @DisplayName("User Service: Get User by Id - Fail")
    public void getUserByIdTestFailed(){
        BDDMockito.doReturn(Optional.empty()).when(mockUserRepo).findById(1);
        Assertions.assertThrows(UserException.class, () -> {
            userService.getUserById(1);
        });
    }

    @Test
    @DisplayName("User Service: Get All Users - Success")
    public void getAllUsersTestSuccess(){
        List<User> users = new ArrayList<>();
        users.add(mockResponseUser1);
        users.add(mockResponseUser2);

        BDDMockito.doReturn(users).when(mockUserRepo).findAll();

        List<User> responseUsers = userService.getAllUsers();
        Assertions.assertIterableEquals(users,responseUsers);
    }

    @Test
    @DisplayName("User Service: Update User - Success")
    public void updateUserTestSuccess() throws UserException{
        List<Listing> listings = new ArrayList<>();
        listings.add(new Listing("Wilmington, DE", "Large", "400000", inputUser));
        listings.add(new Listing("Wilmington, DE", "Small", "250000", inputUser));

        User expectedUserUpdate = new User("Tariq", "Hook", "thook@me.com","1234",listings);

        BDDMockito.doReturn(Optional.of(mockResponseUser1)).when(mockUserRepo).findById(1);
        BDDMockito.doReturn(expectedUserUpdate).when(mockUserRepo).save(ArgumentMatchers.any());

        User actualUser = userService.updateUser(1, expectedUserUpdate);
        Assertions.assertEquals(expectedUserUpdate.toString(), actualUser.toString());

    }

    @Test
    @DisplayName("User Service: Update User - Fail")
    public void updateUserTestFail(){
        List<Listing> listings = new ArrayList<>();
        listings.add(new Listing("Wilmington, DE", "Large", "400000", inputUser));
        listings.add(new Listing("Wilmington, DE", "Small", "250000", inputUser));

        User expectedUserUpdate = new User("Tariq", "Hook", "thook@me.com","1234",listings);

        BDDMockito.doReturn(Optional.empty()).when(mockUserRepo).findById(1);
        Assertions.assertThrows(UserException.class, () ->{
            userService.updateUser(1,expectedUserUpdate);
        });
    }

    @Test
    @DisplayName("User Service: Delete User - Success")
    public void deleteUserTestSuccess() throws UserException{
        BDDMockito.doReturn(Optional.of(mockResponseUser1)).when(mockUserRepo).findById(1);
        Boolean actualResponse = userService.deleteUser(1);
        Assertions.assertTrue(actualResponse);
    }

    @Test
    @DisplayName("User Service: Delete User - Fail")
    public void deleteUserTestFail(){
        BDDMockito.doReturn(Optional.empty()).when(mockUserRepo).findById(1);
        Assertions.assertThrows(UserException.class, () ->{
            userService.deleteUser(1);
        });
    }


}
