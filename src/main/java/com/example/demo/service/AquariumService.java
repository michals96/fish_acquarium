package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Aquarium;
import com.example.demo.model.command.CreateaAquariumCommand;
import com.example.demo.repository.AquariumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AquariumService {
    private final AquariumRepository aquariumRepository;

    public Aquarium save(final CreateaAquariumCommand aquarium) {
        return aquariumRepository.save(Aquarium.builder()
            .name(aquarium.getName())
            .capacity(aquarium.getCapacity())
            .build());
    }

    @Transactional(readOnly = true)
    public List<Aquarium> getAll() {
        return aquariumRepository.findAll();
    }
}
