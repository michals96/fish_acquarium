package com.example.demo.service;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
@DataJpaTest
class AquariumServiceTest {

    @BeforeEach
    void setUp() {
        aquariumService = new AquariumService(aquariumRepository, null);
    }

    @Autowired
    private AquariumRepository aquariumRepository;

    @InjectMocks
    private AquariumService aquariumService;

    @Test
    public void shouldSaveAquarium() {
        Aquarium aquarium = aquariumService.save(new CreateaAquariumCommand("Aquarium", 10));
        assertThat(aquarium).isNotNull();
    }
}