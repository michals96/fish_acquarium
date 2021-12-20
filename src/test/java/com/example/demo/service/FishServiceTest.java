package com.example.demo.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.Aquarium;
import com.example.demo.model.Fish;
import com.example.demo.model.command.CreateFishCommand;
import com.example.demo.repository.AquariumRepository;
import com.example.demo.repository.FishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class FishServiceTest {

    @BeforeEach
    void setUp() {
        fishService = new FishService(fishRepository, aquariumService);
    }

    @Autowired
    private FishRepository fishRepository;

    @InjectMocks
    private FishService fishService;

    @Mock
    private AquariumRepository aquariumRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AquariumService aquariumService;

    @Test
    @Disabled
    void shouldSaveFish() {
        when(aquariumService.getOne(any())).thenReturn(new Aquarium(1L, "test_aq", 10, new ArrayList<>()));
        Fish fish = fishService.save(new CreateFishCommand("test_fish", "test_type", BigDecimal.ONE, "1"));
        assertThat(fish).isNotNull();
    }

    @Test
    @Disabled
    void shouldGetAllFish() {
        when(aquariumService.getOne(any())).thenReturn(new Aquarium(1L, "test_aq", 10, new ArrayList<>()));

        fishService.save(new CreateFishCommand("test_fish", "test_type", BigDecimal.ONE, "1"));
        fishService.save(new CreateFishCommand("test_fish", "test_type", BigDecimal.ONE, "1"));

        List<Fish> all = fishService.getAll();

        assertThat(all).isNotEmpty();
        assertThat(all).isNotNull();
        assertThat(all.size()).isEqualTo(2);
    }
}