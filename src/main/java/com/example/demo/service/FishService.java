package com.example.demo.service;

import java.util.List;

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

    public Fish save(final CreateFishCommand fish) {
        return fishRepository.save(Fish.builder()
            .name(fish.getName())
            .price(fish.getPrice())
            .build());
    }

    @Transactional(readOnly = true)
    public List<Fish> getAll() {
        return fishRepository.findAll();
    }
}
