package com.example.demo.api;

import java.math.BigDecimal;

import com.example.demo.DemoApplication;
import com.example.demo.model.command.CreateAquariumCommand;
import com.example.demo.model.command.CreateFishCommand;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureMockMvc
class AquariumControllerIT {
    protected final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private MockMvc postman;

    @Test
    void shouldAddSingleAquarium() throws Exception {
        postman.perform(post("/aquarium")
            .contentType(APPLICATION_JSON)
            .content(toJson(dummyRequest())))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name", is(equalTo("OK"))));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "FISHERMAN")
    void shouldMoveFishToDifferentAquarium() throws Exception {
        postman.perform(post("/aquarium")
            .contentType(APPLICATION_JSON)
            .content(toJson(new CreateAquariumCommand("OK", 2))))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name", is(equalTo("OK"))));

        postman.perform(post("/aquarium")
            .contentType(APPLICATION_JSON)
            .content(toJson(new CreateAquariumCommand("OK2", 2))))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name", is(equalTo("OK2"))));

        postman.perform(post("/fish")
            .contentType(APPLICATION_JSON)
            .content(toJson(new CreateFishCommand("OK", "type", BigDecimal.ONE, "1"))))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name", is(equalTo("OK"))));

        postman.perform(put("/aquarium/1/2")
            .contentType(APPLICATION_JSON)
            .content(toJson(dummyRequest())))
            .andExpect(status().isNoContent());
    }

    @Test
    void shouldNotMoveFishToDifferentAquariumOnMissingCredentials() throws Exception {
        postman.perform(put("/aquarium/1/1")
            .contentType(APPLICATION_JSON)
            .content(toJson(dummyRequest())))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @Disabled
    void shouldGetAquariumsList() throws Exception {
        postman.perform(post("/aquarium")
            .contentType(APPLICATION_JSON)
            .content(toJson(new CreateAquariumCommand("OK3", 2))))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name", is(equalTo("OK3"))));

        postman.perform(post("/aquarium")
            .contentType(APPLICATION_JSON)
            .content(toJson(new CreateAquariumCommand("OK4", 2))))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name", is(equalTo("OK4"))));

        ResultActions perform = postman.perform(get("/aquarium"));
        perform.andExpect(status().isOk());
    }

    @Test
    @Disabled
    void shouldGetFishFromAquarium() throws Exception {
        postman.perform(get("/aquarium")
            .contentType(APPLICATION_JSON)
            .content(toJson(dummyRequest())))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "sales", password = "sales", roles = "SALESMAN")
    void shouldRemoveAquarium() throws Exception {
        postman.perform(delete("/aquarium/1")
            .contentType(APPLICATION_JSON)
            .content(toJson(dummyRequest())))
            .andExpect(status().isNoContent());
    }

    @Test
    void shouldNotRemoveAquariumOnMissingAuthorization() throws Exception {
        postman.perform(delete("/aquarium/1")
            .contentType(APPLICATION_JSON)
            .content(toJson(dummyRequest())))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "sales", password = "sales", roles = "SALESMAN")
    void shouldNotRemoveAquariumOnWrongId() throws Exception {
        postman.perform(delete("/aquarium/100")
            .contentType(APPLICATION_JSON)
            .content(toJson(dummyRequest())))
            .andExpect(status().isNotFound());
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