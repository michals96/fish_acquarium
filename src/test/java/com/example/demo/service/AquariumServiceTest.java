package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Aquarium;
import com.example.demo.model.command.CreateaAquariumCommand;
import com.example.demo.repository.AquariumRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
@DataJpaTest
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
    public void shouldSaveAquarium() {
        Aquarium aquarium = aquariumService.save(new CreateaAquariumCommand("Aquarium", 10));
        assertThat(aquarium).isNotNull();
    }

    @Test
    public void shouldGetAllAquariums() {
        aquariumService.save(new CreateaAquariumCommand("Aquarium", 10));
        aquariumService.save(new CreateaAquariumCommand("Aquarium", 10));

        List<Aquarium> all = aquariumService.getAll();

        assertThat(all).isNotEmpty();
        assertThat(all).isNotNull();
        assertThat(all.size()).isEqualTo(2);
    }

    @Test
    public void shouldGetOneAquarium() {
        aquariumService.save(new CreateaAquariumCommand("Aquarium", 10));

        Aquarium one = aquariumService.getOne(1L);

        assertThat(one).isNotNull();
    }

    //TODO
    @Test
    public void shouldMoveFishToDifferentAquarium() {

    }

    @Test
    public void shouldRemoveAquarium() {

    }
}