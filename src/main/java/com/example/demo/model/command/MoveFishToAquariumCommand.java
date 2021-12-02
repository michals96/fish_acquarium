package com.example.demo.model.command;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MoveFishToAquariumCommand {
    @NotNull
    private Long fishId;
    @NotNull
    private Long aquariumId;
}
