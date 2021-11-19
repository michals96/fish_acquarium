package com.example.demo.model.command;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateFishCommand {
    @NotNull
    private String name;
    @NotNull
    private String type;
    @NotNull
    private BigDecimal price;
}
