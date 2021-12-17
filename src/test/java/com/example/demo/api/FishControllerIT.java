package com.example.demo.api;

import java.math.BigDecimal;

import com.example.demo.DemoApplication;
import com.example.demo.model.command.CreateAquariumCommand;
import com.example.demo.model.command.CreateFishCommand;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureMockMvc
class FishControllerIT {
    protected final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private MockMvc postman;

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "FISHERMAN")
    void shouldAddSingleFish() throws Exception {
        postman.perform(post("/aquarium")
            .contentType(APPLICATION_JSON)
            .content(toJson(new CreateAquariumCommand("OK", 2))))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name", is(equalTo("OK"))));

        postman.perform(post("/fish")
            .contentType(APPLICATION_JSON)
            .content(toJson(dummyRequest())))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name", is(equalTo("OK"))));
    }

    @Test
    void shouldNotAddSingleFishOnMissingAuthorization() throws Exception {
        postman.perform(post("/aquarium")
            .contentType(APPLICATION_JSON)
            .content(toJson(new CreateAquariumCommand("OK", 2))))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name", is(equalTo("OK"))));

        postman.perform(post("/fish")
            .contentType(APPLICATION_JSON)
            .content(toJson(dummyRequest())))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldGetFishList() throws Exception {
        postman.perform(get("/fish")
            .contentType(APPLICATION_JSON)
            .content(toJson(dummyRequest())))
            .andExpect(status().isOk());
    }

    private CreateFishCommand dummyRequest() {
        return new CreateFishCommand("OK", "type", BigDecimal.ONE, "1");
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