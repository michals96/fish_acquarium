package com.example.demo.validation.logic;

import java.util.Optional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.example.demo.model.Aquarium;
import com.example.demo.repository.AquariumRepository;
import com.example.demo.validation.annotation.LimitCapacity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LimitCapacityValidator implements ConstraintValidator<LimitCapacity, String> {
    private final AquariumRepository aquariumRepository;

    @Override
    public boolean isValid(String aquariumId, ConstraintValidatorContext context) {
        Optional<Aquarium> byId = aquariumRepository.findById(Long.valueOf(aquariumId));
        return byId.isPresent() && byId.get().validateIfPossibleToAddFish();
    }
}
