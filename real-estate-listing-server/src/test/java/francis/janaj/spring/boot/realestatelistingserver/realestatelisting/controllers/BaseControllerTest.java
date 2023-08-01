package francis.janaj.spring.boot.realestatelistingserver.realestatelisting.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class BaseControllerTest {
    protected static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
