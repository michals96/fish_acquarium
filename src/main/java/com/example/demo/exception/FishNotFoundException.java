package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class FishNotFoundException extends EntityNotFoundException {
    public FishNotFoundException(String email) {
        super(String.format("Could not find fish with email: %s.", email));
    }

    public FishNotFoundException(long id) {
        super("Could not find fish with id: " + id);
    }
}