package com.example.demo.api;

import java.util.List;

import com.example.demo.model.Aquarium;
import com.example.demo.service.AquariumService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/aquarium")
@RequiredArgsConstructor
@Slf4j
public class AquariumController {

    private final AquariumService aquariumService;

    @PostMapping
    public void createAquarium(@RequestBody Aquarium aquarium) {
        aquariumService.save(aquarium);
    }

    @GetMapping
    public List<Aquarium> getAquarium() {
        return aquariumService.getAll();
    }
}
