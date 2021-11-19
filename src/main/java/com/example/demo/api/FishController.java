package com.example.demo.api;

import java.util.List;

import com.example.demo.model.Fish;
import com.example.demo.service.FishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/fish")
@RequiredArgsConstructor
@Slf4j
public class FishController {
    private final FishService fishService;

    @PostMapping
    public Fish createFish(@RequestBody Fish fish) {
        return fishService.save(fish);
    }

    @GetMapping
    public List<Fish> getFishes() {
        return fishService.getAll();
    }
}
