package com.example.demo.model.command;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateaAquariumCommand {
    @NotNull
    private String name;
    @NotNull
    private Integer capacity;
}
