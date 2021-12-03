package com.example.demo.service;

import java.util.List;

import com.example.demo.exception.AquariumNotFoundException;
import com.example.demo.model.Aquarium;
import com.example.demo.model.Fish;
import com.example.demo.model.command.CreateaAquariumCommand;
import com.example.demo.repository.AquariumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AquariumService {
    private final AquariumRepository aquariumRepository;
    private final FishService fishService;

    public Aquarium save(final CreateaAquariumCommand aquarium) {
        return aquariumRepository.save(Aquarium.builder()
            .name(aquarium.getName())
            .capacity(aquarium.getCapacity())
            .build());
    }

    public Aquarium save(final Aquarium aquarium) {
        return aquariumRepository.save(aquarium);
    }

    @Transactional(readOnly = true)
    public List<Aquarium> getAll() {
        return aquariumRepository.findAll();
    }

    public Aquarium getOne(final Long id) {
        return aquariumRepository
            .findById(id)
            .orElseThrow(() -> new AquariumNotFoundException(id));
    }

    @Transactional
    public boolean moveFish(final Long sourceAqId, final Long targetAqId) {
        Aquarium sourceAquarium = getOne(sourceAqId);
        Aquarium targetAquarium = getOne(targetAqId);

        if (!targetAquarium.validateIfPossibleToAddFish()) {
            return false;
        }

        List<Fish> fishes = sourceAquarium.getFishes();
        targetAquarium.addFishes(fishes);

        return true;
    }

    @Transactional
    public void remove(final Long id) {
        aquariumRepository.deleteById(id);
    }
}
