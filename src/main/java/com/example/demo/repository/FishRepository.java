package com.example.demo.repository;


import com.example.demo.model.Fish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface FishRepository extends JpaRepository<Fish, Long>, NameRepository<Fish> {

}
