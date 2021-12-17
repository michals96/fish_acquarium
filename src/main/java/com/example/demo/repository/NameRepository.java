package com.example.demo.repository;

import java.util.Optional;

public interface NameRepository<T> {
    Optional<T> findByName(String name);
    boolean existsByName(String name);
}
