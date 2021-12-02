package com.example.demo.model;

import java.math.BigDecimal;
import javax.persistence.*;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
public class Fish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;
    private String name;
    // Todo: ENUM?? to be investigated
    private String type;
    private BigDecimal price;
    @ManyToOne(fetch = FetchType.LAZY)
    private Aquarium aquarium;
}
