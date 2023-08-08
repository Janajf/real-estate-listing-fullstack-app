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
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.junit.jupiter.MockitoExtension;
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

        Listing listing1 = new Listing( "Wilmington, DE", "Large", "400000", 1);
        Listing listing2 = new Listing( "Wilmington, DE", "Med", "350000",1);

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
        // return mock response user 1 when the save method is called from mock user repo
        BDDMockito.doReturn(mockResponseUser1).when(mockUserRepo).save(ArgumentMatchers.any());
        // make a call the create method passing the input user ... assigning the returned value to returned user
        User returnedUser = userService.create(inputUser);
        // make sure returned user is not null
        Assertions.assertNotNull(returnedUser, "User should not be null");
        // make sure returned user has the value of 1 for the id
        Assertions.assertEquals(returnedUser.getId(), 1);
    }

    @Test
    @DisplayName("User Service: Get User by Id - Success")
    public void getUserByIdTestSuccess() throws UserException{
        // return an Optional of mock response user 1 when the findById method is called from mock user repo
        BDDMockito.doReturn(Optional.of(mockResponseUser1)).when(mockUserRepo).findById(1);
        // make a call to the getUserById method from the user service ... assigned the returned value to found user
        User foundUser = userService.getUserById(1);
        // make sure the value of the mock response user is equal to found user
        Assertions.assertEquals(mockResponseUser1.toString(), foundUser.toString());
    }

    @Test
    @DisplayName("User Service: Get User by Id - Fail")
    public void getUserByIdTestFailed(){
        // return an empty Optional when the findById method is called from mock user repo
        BDDMockito.doReturn(Optional.empty()).when(mockUserRepo).findById(1);
        // make sure the user exception is thrown when the getUserById method is called and an empty optional is returned
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

        // return users when the findAll() method is called from the mockUserRepo
        BDDMockito.doReturn(users).when(mockUserRepo).findAll();

        // call the getAllUsers() method from the userService ... assign the result to responseUsers
        List<User> responseUsers = userService.getAllUsers();
        // make sure users and response users is equal
        Assertions.assertIterableEquals(users,responseUsers);
    }

    @Test
    @DisplayName("User Service: Update User - Success")
    public void updateUserTestSuccess() throws UserException{
        List<Listing> listings = new ArrayList<>();
        listings.add(new Listing("Wilmington, DE", "Large", "400000", 1));
        listings.add(new Listing("Wilmington, DE", "Small", "250000", 1));

        User expectedUserUpdate = new User("Tariq", "Hook", "thook@me.com","1234",listings);

        // return an Optional of mock response user 1 when the findById() method is called from the mock user repo
        BDDMockito.doReturn(Optional.of(mockResponseUser1)).when(mockUserRepo).findById(1);
        // return expectedUserUpdate when the save() method is called from the mockUserRepo
        BDDMockito.doReturn(expectedUserUpdate).when(mockUserRepo).save(ArgumentMatchers.any());

        // call the updateUser passing the id of 1 and expectedUserUpdate
        User actualUser = userService.updateUser(1, expectedUserUpdate);
        // make sure expectedUserUpdate and actualUser are equal
        Assertions.assertEquals(expectedUserUpdate.toString(), actualUser.toString());

    }

    @Test
    @DisplayName("User Service: Update User - Fail")
    public void updateUserTestFail(){
        List<Listing> listings = new ArrayList<>();
        listings.add(new Listing("Wilmington, DE", "Large", "400000", 1));
        listings.add(new Listing("Wilmington, DE", "Small", "250000", 1));

        User expectedUserUpdate = new User("Tariq", "Hook", "thook@me.com","1234",listings);

        // return an empty Optional when the findById() method is called from the mock user repo
        BDDMockito.doReturn(Optional.empty()).when(mockUserRepo).findById(1);
        // make sure the User exception is thrown when the updateUser() method is called
        Assertions.assertThrows(UserException.class, () ->{
            userService.updateUser(1,expectedUserUpdate);
        });
    }

    @Test
    @DisplayName("User Service: Delete User - Success")
    public void deleteUserTestSuccess() throws UserException{
        // return an Optional of mock response user 1 when findById() is called from the mock user repo
        BDDMockito.doReturn(Optional.of(mockResponseUser1)).when(mockUserRepo).findById(1);
        // call the deleteUser() method from the userService ... assign the value of the result to actualResponse
        Boolean actualResponse = userService.deleteUser(1);
        // check to see if the result is true
        Assertions.assertTrue(actualResponse);
    }

    @Test
    @DisplayName("User Service: Delete User - Fail")
    public void deleteUserTestFail(){
        // return an empty Optional when the findById() method is called from mockUserRepo
        BDDMockito.doReturn(Optional.empty()).when(mockUserRepo).findById(1);
        // throw the user exception when the deleteUser() method is called
        Assertions.assertThrows(UserException.class, () ->{
            userService.deleteUser(1);
        });
    }


}
