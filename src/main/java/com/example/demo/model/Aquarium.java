package com.example.demo.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
public class Aquarium {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;
    private String name;
    private Integer capacity;
    @OneToMany(mappedBy = "aquarium", cascade = CascadeType.ALL)
    private Set<Fish> fishes = new HashSet<Fish>();

    public void addFish(Fish fish) {
        fish.setAquarium(this);
        this.fishes.add(fish);
    }
}
