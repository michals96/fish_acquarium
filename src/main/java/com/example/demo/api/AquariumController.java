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
    public Aquarium createAquarium(@RequestBody Aquarium aquarium) {
        return aquariumService.save(aquarium);
    }

    @GetMapping
    public List<Aquarium> getAquariums() {
        return aquariumService.getAll();
    }
}
