package francis.janaj.spring.boot.realestatelistingserver.realestatelisting.controllers;

import francis.janaj.spring.boot.realestatelistingserver.domain.core.exceptions.UserException;
import francis.janaj.spring.boot.realestatelistingserver.domain.realEstateListing.models.Listing;
import francis.janaj.spring.boot.realestatelistingserver.domain.realEstateListing.models.User;
import francis.janaj.spring.boot.realestatelistingserver.domain.realEstateListing.services.UserService;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class UserControllerTest extends BaseControllerTest{

    @MockBean
    private UserService mockUserService;

    @Autowired
    private MockMvc mockMvc;

    private User inputUser;
    private User mockResponseUser1;
    private User mockResponseUser2;

    @BeforeEach
    public void setUp(){
        List<Listing> listings = new ArrayList<>();
        List<Listing> listings1 = new ArrayList<>();
        List<Listing> listings2 = new ArrayList<>();

        Listing listing1 = new Listing( "Wilmington, DE", "Large", "400000", inputUser);
        Listing listing2 = new Listing( "Wilmington, DE", "Med", "350000",inputUser);

        inputUser = new User( "Tariq", "Hook", "thook@me.com","1234",listings1);

        listings1.add(listing1);
        listings2.add(listing2);

        mockResponseUser1 = new User( "Test User firstname 01", "Test User firstname 01", "test@me.com", "1234",listings);
        mockResponseUser1.setId(1);
        mockResponseUser2 = new User( "Test User firstname 02", "Test User firstname 02", "test@me2.com", "5678",listings2);
        mockResponseUser2.setId(2);
    }

    @Test
    @DisplayName("User Post: /users - Success")
    public void createUserRequestSuccess() throws Exception{
        BDDMockito.doReturn(mockResponseUser1).when(mockUserService).create(any());

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(inputUser)))

                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname", Is.is("Test User firstname 01")));

    }

    @Test
    @DisplayName("GET /users/1 - Found")
    public void getUserByIdTestSuccess() throws Exception{
        BDDMockito.doReturn(mockResponseUser1).when(mockUserService).getUserById(1);

        mockMvc.perform(get("/users/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstname", is("Test User firstname 01")));
    }

    @Test
    @DisplayName("GET /users/1 - Not Found")
    public void getUserByIdTestFail() throws Exception {
        BDDMockito.doThrow(new UserException("Not Found")).when(mockUserService).getUserById(1);
        mockMvc.perform(get("/users/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /users/1 - Success")
    public void updateUserTestSuccess() throws Exception{
        List<Listing> listings = new ArrayList<>();
        User expectedUserUpdate = new User("After update Test User firstname 01", "Test User firstname 01", "test@me.com", "1234",listings);
        expectedUserUpdate.setId(1);

        BDDMockito.doReturn(expectedUserUpdate).when(mockUserService).updateUser(any(),any());
        mockMvc.perform(put("/users/{id}",1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(inputUser)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstname", is("After update Test User firstname 01")));

    }
    @Test
    @DisplayName("PUT /users/1 - Not Found")
    public void putUserTestNotFound() throws Exception{
        BDDMockito.doThrow(new UserException("Not Found")).when(mockUserService).updateUser(any(), any());
        mockMvc.perform(put("/users/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(inputUser)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /users/1 - Success")
    public void deleteUserTestSuccess() throws Exception{
        BDDMockito.doReturn(true).when(mockUserService).deleteUser(any());
        mockMvc.perform(delete("/users/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /users/1 - Not Found")
    public void deleteUserTestNotFound() throws Exception{
        BDDMockito.doThrow(new UserException("Not Found")).when(mockUserService).deleteUser(any());
        mockMvc.perform(delete("/users/{id}", 1))
                .andExpect(status().isNotFound());
    }

}
