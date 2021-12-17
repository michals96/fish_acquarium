package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;
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
    private List<Fish> fish = new ArrayList<>();

    public void addFish(Fish fish) {
        fish.setAquarium(this);
        this.fish.add(fish);
    }

    public void addFish(List<Fish> fishList) {
        for(Fish fish : fishList) {
            fish.setAquarium(this);
        }
        this.fish.addAll(fishList);
    }

    public boolean validateIfPossibleToAddFish() {
        return (fish.size() < capacity);
    }
}
