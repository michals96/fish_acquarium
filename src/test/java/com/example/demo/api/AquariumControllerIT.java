package com.example.demo.api;

import java.util.List;

import com.example.demo.DemoApplication;
import com.example.demo.model.Aquarium;
import com.example.demo.model.command.CreateAquariumCommand;
import com.example.demo.service.AquariumService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureMockMvc
class AquariumControllerIT {
    protected final ObjectMapper mapper = new ObjectMapper();

    @MockBean
    AquariumService aquariumService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc postman;

    @Test
    void shouldAddSingleAquarium() throws Exception {
        Aquarium aquarium = Aquarium.builder().name("Aq1").capacity(3).build();
        when(aquariumService.save(any())).thenReturn(aquarium);

        postman.perform(post("/aquarium")
            .contentType(APPLICATION_JSON)
            .content(toJson(dummyRequest())))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name", is(equalTo("Aq1"))));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "FISHERMAN")
    void shouldMoveFishToDifferentAquarium() throws Exception {
        when(aquariumService.moveFish(any(), any())).thenReturn(true);

        postman.perform(put("/aquarium/1/1")
            .contentType(APPLICATION_JSON)
            .content(toJson(dummyRequest())))
            .andExpect(status().isNoContent());
    }

    @Test
    void shouldNotMoveFishToDifferentAquariumOnMissingCredentials() throws Exception {
        when(aquariumService.moveFish(any(), any())).thenReturn(true);

        postman.perform(put("/aquarium/1/1")
            .contentType(APPLICATION_JSON)
            .content(toJson(dummyRequest())))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldGetAquariumsList() throws Exception {
        Aquarium aquarium = Aquarium.builder().name("Aq1").capacity(3).build();
        when(aquariumService.getAll()).thenReturn(List.of(aquarium));
        postman.perform(get("/aquarium")
            .contentType(APPLICATION_JSON)
            .content(toJson(dummyRequest())))
            .andExpect(status().isOk());
    }

    @Test
    void shouldGetFishesFromAquarium() throws Exception {
        Aquarium aquarium = Aquarium.builder().name("Aq1").capacity(3).build();
        when(aquariumService.getAll()).thenReturn(List.of(aquarium));

        postman.perform(get("/aquarium")
            .contentType(APPLICATION_JSON)
            .content(toJson(dummyRequest())))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "sales", password = "sales", roles = "SALESMAN")
    void shouldRemoveAquarium() throws Exception {
        when(aquariumService.remove(any())).thenReturn(true);

        postman.perform(delete("/aquarium/1")
            .contentType(APPLICATION_JSON)
            .content(toJson(dummyRequest())))
            .andExpect(status().isNoContent());
    }

    @Test
    void shouldNotRemoveAquariumOnMissingAuthorization() throws Exception {
        when(aquariumService.remove(any())).thenReturn(true);

        postman.perform(delete("/aquarium/1")
            .contentType(APPLICATION_JSON)
            .content(toJson(dummyRequest())))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "sales", password = "sales", roles = "SALESMAN")
    void shouldNotRemoveAquariumOnWrongId() throws Exception {
        when(aquariumService.remove(any())).thenReturn(false);

        postman.perform(delete("/aquarium/1")
            .contentType(APPLICATION_JSON)
            .content(toJson(dummyRequest())))
            .andExpect(status().isBadRequest());
    }

    private CreateAquariumCommand dummyRequest() {
        return new CreateAquariumCommand("OK", 2);
    }

    protected String toJson(final Object anObject) {
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        try {
            return ow.writeValueAsString(anObject);
        } catch (final JsonProcessingException e) {
            throw new RuntimeException("Could not write as json: " + anObject, e);
        }
    }
}