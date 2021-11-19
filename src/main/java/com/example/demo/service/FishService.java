package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Fish;
import com.example.demo.repository.FishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FishService {
    private final FishRepository fishRepository;

    public Fish save(final Fish fish) {
        return fishRepository.save(fish);
    }

    public List<Fish> getAll() {
        return fishRepository.findAll();
    }
}
