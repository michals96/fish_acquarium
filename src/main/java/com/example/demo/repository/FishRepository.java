package com.example.demo.repository;

import java.util.List;

import com.example.demo.model.Fish;
import org.springframework.data.repository.CrudRepository;

public interface FishRepository extends CrudRepository<Fish, Long> {
    List<Fish> findAll();
    Fish getByName(String name);
}
