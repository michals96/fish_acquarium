package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Aquarium;
import com.example.demo.model.Fish;
import com.example.demo.model.command.CreateFishCommand;
import com.example.demo.repository.FishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FishService {
    private final FishRepository fishRepository;
    private final AquariumService aquariumService;

    public Fish save(final CreateFishCommand fish) {
        Aquarium aquarium = aquariumService.getOne(Long.valueOf(fish.getAquariumId()));

        if(!aquarium.validateIfPossibleToAddFish()) {
            return null;
        }

        return fishRepository.save(Fish.builder()
            .name(fish.getName())
            .type(fish.getType())
            .price(fish.getPrice())
            .aquarium(aquarium)
            .build());
    }

    @Transactional(readOnly = true)
    public List<Fish> getAll() {
        return fishRepository.findAll();
    }
}
