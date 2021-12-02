package com.example.demo.model.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FishDto{
    private long id;
    private String name;
    private String type;
    private BigDecimal price;
    //private AquariumDto aquarium;
}
