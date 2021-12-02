package com.example.demo.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    private List<Fish> fishes = new ArrayList<>();

    public void addFish(Fish fish) {
        fish.setAquarium(this);
        this.fishes.add(fish);
    }
}
