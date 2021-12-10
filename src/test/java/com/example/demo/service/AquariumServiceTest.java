package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Aquarium;
import com.example.demo.model.Fish;
import com.example.demo.model.command.CreateaAquariumCommand;
import com.example.demo.repository.AquariumRepository;
import org.junit.jupiter.api.BeforeEach;
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

@ExtendWith({MockitoExtension.class})
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AquariumServiceTest {

    @BeforeEach
    void setUp() {
        aquariumService = new AquariumService(aquariumRepository, modelMapper);
    }

    @Autowired
    private AquariumRepository aquariumRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AquariumService aquariumService;

    @Test
    void shouldSaveAquarium() {
        Aquarium aquarium = aquariumService.save(new CreateaAquariumCommand("Aquarium", 10));
        assertThat(aquarium).isNotNull();
    }

    @Test
    void shouldGetAllAquariums() {
        aquariumService.save(new CreateaAquariumCommand("Aquarium", 10));
        aquariumService.save(new CreateaAquariumCommand("Aquarium", 10));

        List<Aquarium> all = aquariumService.getAll();

        assertThat(all).isNotEmpty();
        assertThat(all).isNotNull();
        assertThat(all.size()).isEqualTo(2);
    }

    @Test
    void shouldGetOneAquarium() {
        aquariumService.save(new CreateaAquariumCommand("Aquarium", 10));
        aquariumService.save(new CreateaAquariumCommand("Aquarium", 10));

        Aquarium aquarium = aquariumService.getOne(1L);

        assertThat(aquarium).isNotNull();
    }

    @Test
    void shouldMoveFishToDifferentAquarium() {
        Aquarium firstAquarium = aquariumService.save(new CreateaAquariumCommand("Aquarium", 10));
        Aquarium secondAquarium = aquariumService.save(new CreateaAquariumCommand("Aquarium", 10));

        Fish fish = Fish.builder().name("test_fish").build();
        firstAquarium.addFish(fish);

        assertThat(firstAquarium.getFishes().size()).isEqualTo(1);
        assertThat(secondAquarium.getFishes().size()).isZero();

        aquariumService.moveFish(1L, 2L);

        assertThat(firstAquarium.getFishes().size()).isZero();
        assertThat(secondAquarium.getFishes().size()).isEqualTo(1);

    }

    @Test
    void shouldRemoveAquarium() {
        aquariumService.save(new CreateaAquariumCommand("Aquarium", 10));
        aquariumService.save(new CreateaAquariumCommand("Aquarium", 10));

        aquariumService.remove(1L);
        List<Aquarium> aquariums = aquariumService.getAll();

        assertThat(aquariums.size()).isEqualTo(1);
    }
}