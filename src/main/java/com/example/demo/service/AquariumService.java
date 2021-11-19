package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Aquarium;
import com.example.demo.repository.AquariumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AquariumService {
    private final AquariumRepository aquariumRepository;

    public void save(final Aquarium aquarium) {
        aquariumRepository.save(aquarium);
    }

    public List<Aquarium> getAll() {
        return aquariumRepository.findAll();
    }
}
