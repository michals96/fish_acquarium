package com.example.demo.api;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.DemoApplication;
import com.example.demo.model.Aquarium;
import com.example.demo.model.Fish;
import com.example.demo.model.command.CreateFishCommand;
import com.example.demo.repository.AquariumRepository;
import com.example.demo.service.FishService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureMockMvc
class FishControllerTest {
    protected final ObjectMapper mapper = new ObjectMapper();
    @MockBean
    FishService fishService;
    @MockBean
    AquariumRepository aquariumRepository;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc postman;

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "FISHERMAN")
    void shouldAddSingleFish() throws Exception {
        Fish fish = Fish.builder().name("Fish").build();
        Aquarium aquarium = Aquarium.builder().name("Aq1").capacity(3).fishes(new ArrayList<>()).build();
        aquarium.getFishes().add(fish);
        when(fishService.save(any())).thenReturn(fish);
        when(aquariumRepository.findById(any())).thenReturn(Optional.of(aquarium));

        postman.perform(post("/fish")
            .contentType(APPLICATION_JSON)
            .content(toJson(dummyRequest())))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name", is(equalTo("Fish"))));
    }

    @Test
    void shouldNotAddSingleFishOnMissingAuthorization() throws Exception {
        Fish fish = Fish.builder().name("Fish").build();
        Aquarium aquarium = Aquarium.builder().name("Aq1").capacity(3).fishes(new ArrayList<>()).build();
        aquarium.getFishes().add(fish);
        when(fishService.save(any())).thenReturn(fish);
        when(aquariumRepository.findById(any())).thenReturn(Optional.of(aquarium));

        postman.perform(post("/fish")
            .contentType(APPLICATION_JSON)
            .content(toJson(dummyRequest())))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldGetFishList() throws Exception {
        Fish fish = Fish.builder().name("Fish").build();
        when(fishService.getAll()).thenReturn(List.of(fish));

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