package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AquariumNotFoundException extends EntityNotFoundException {
    public AquariumNotFoundException(String email) {
        super(String.format("Could not find aquarium with email: %s.", email));
    }

    public AquariumNotFoundException(long id) {
        super("Could not find aquarium with id: " + id);
    }
}
