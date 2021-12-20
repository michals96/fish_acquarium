package com.example.demo.validation.logic;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.example.demo.repository.NameRepository;
import com.example.demo.validation.annotation.UniqueName;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Scope("prototype")
public class UniqueNameValidator implements ConstraintValidator<UniqueName, String> {

    private final ApplicationContext springApplicationContext;
    private Class<? extends NameRepository> repositoryType;

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        NameRepository nameRepository = springApplicationContext.getBean(repositoryType);
        return !nameRepository.existsByName(name);
    }

    @Override
    public void initialize(final UniqueName constraintAnnotation) {
        this.repositoryType = constraintAnnotation.type();
    }
}
