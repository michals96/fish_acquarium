package com.example.demo.model.command;

import javax.validation.constraints.NotNull;

import com.example.demo.model.Aquarium;
import com.example.demo.validation.annotation.UniqueName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@AllArgsConstructor
@Validated
public class CreateaAquariumCommand {
    @NotNull
    @UniqueName(message="AQUARIUM_ALREADY_EXISTS", type = Aquarium.class)
    private String name;
    @NotNull
    private Integer capacity;
}
