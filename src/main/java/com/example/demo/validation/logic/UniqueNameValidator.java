package com.example.demo.validation.logic;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.example.demo.repository.AquariumRepository;
import com.example.demo.repository.FishRepository;
import com.example.demo.validation.annotation.UniqueName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UniqueNameValidator implements ConstraintValidator<UniqueName, String> {
    private final FishRepository fishRepository;
    private final AquariumRepository aquariumRepository;
    private String classType;

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        if(classType.equals("Fish")) {
            return fishRepository.getByName(name) == null;
        } else if(classType.equals("Aquarium")) {
            return aquariumRepository.getByName(name) == null;
        } else return false;
    }

    @Override
    public void initialize(final UniqueName constraintAnnotation) {
        this.classType = constraintAnnotation.type().getSimpleName();
    }
}
