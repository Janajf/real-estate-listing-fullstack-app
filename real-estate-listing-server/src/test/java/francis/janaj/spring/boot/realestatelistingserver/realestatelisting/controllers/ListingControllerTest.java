package francis.janaj.spring.boot.realestatelistingserver.realestatelisting.controllers;

import francis.janaj.spring.boot.realestatelistingserver.domain.core.exceptions.ListingException;
import francis.janaj.spring.boot.realestatelistingserver.domain.realEstateListing.models.Listing;
import francis.janaj.spring.boot.realestatelistingserver.domain.realEstateListing.models.User;
import francis.janaj.spring.boot.realestatelistingserver.domain.realEstateListing.services.ListingService;
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
public class ListingControllerTest extends BaseControllerTest{

    @MockBean
    private ListingService mockListingService;

    @Autowired
    private MockMvc mockMvc;

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
    @DisplayName("Listing Service: Create Listing - Success")
    public void createListingRequestSuccess() throws Exception{
        BDDMockito.doReturn(mockResponseListing1).when(mockListingService).create(any());

        mockMvc.perform(MockMvcRequestBuilders.post("/listings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(inputListing)))

                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.location", Is.is("Wilmington, DE")));
    }

    @Test
    @DisplayName("GET /listings/1 - Found")
    public void getListingByIdTestSuccess() throws Exception{
        BDDMockito.doReturn(mockResponseListing1).when(mockListingService).getListingById(1);

        mockMvc.perform(get("/listings/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.location", is("Wilmington, DE")));
    }

    @Test
    @DisplayName("GET /listings/1 - Not Found")
    public void getListingByIdTestFail() throws Exception{
        BDDMockito.doThrow(new ListingException("Not Found")).when(mockListingService).getListingById(1);
        mockMvc.perform(get("/listings/{id}",1))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /listings/1 - Success")
    public void updateListingTestSuccess() throws Exception{
        List<Listing> listings = new ArrayList<>();
        User user1 = new User("Tariq", "Hook", "thook@me.com","1234",listings);
        user1.setId(1);
        Listing expectedListingUpdate = new Listing("After Update Location", "Med", "350000", user1);
        expectedListingUpdate.setId(1);

        BDDMockito.doReturn(expectedListingUpdate).when(mockListingService).updateListing(any(),any());
        mockMvc.perform(put("/listings/{id}",1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(inputListing)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.location", is("After Update Location")));
    }

    @Test
    @DisplayName("PUT /listings/1 - Not Found")
    public void putListingTestNotFound() throws Exception{
        BDDMockito.doThrow(new ListingException("Not Found")).when(mockListingService).updateListing(any(),any());
        mockMvc.perform(put("/listings/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(inputListing)))
                .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("DELETE /listings/1 - Success")
    public void deleteListingTestSuccess() throws Exception{
        BDDMockito.doReturn(true).when(mockListingService).deleteListing(any());
        mockMvc.perform(delete("/listings/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /listings/1 - Not Found")
    public void deleteListingTestNotFound() throws Exception{
        BDDMockito.doThrow(new ListingException("Not Found")).when(mockListingService).deleteListing(any());
        mockMvc.perform(delete("/listings/{id}", 1))
                .andExpect(status().isNotFound());
    }






}
