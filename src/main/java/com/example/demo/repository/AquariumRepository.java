package com.example.demo.repository;

import com.example.demo.model.Aquarium;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AquariumRepository extends JpaRepository<Aquarium, Long>, NameRepository<Aquarium> {
}
