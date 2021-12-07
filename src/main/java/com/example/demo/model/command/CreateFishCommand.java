package com.example.demo.model.command;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;

import com.example.demo.model.Fish;
import com.example.demo.validation.annotation.LimitCapacity;
import com.example.demo.validation.annotation.UniqueName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateFishCommand {
    @NotNull
    @UniqueName(message="FISH_ALREADY_EXISTS", type = Fish.class)
    private String name;
    @NotNull
    private String type;
    @NotNull
    private BigDecimal price;
    @NotNull
    @LimitCapacity(message="AQUARIUM_CAPACITY_EXCEEDED")
    private String aquariumId;
}
