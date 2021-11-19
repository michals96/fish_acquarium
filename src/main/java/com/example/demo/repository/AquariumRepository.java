package com.example.demo.repository;

import java.util.List;

import com.example.demo.model.Aquarium;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AquariumRepository extends CrudRepository<Aquarium, Long> {
    List<Aquarium> findAll();
}
