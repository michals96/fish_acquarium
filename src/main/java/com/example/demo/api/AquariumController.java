package com.example.demo.api;

import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.model.Aquarium;
import com.example.demo.model.command.CreateaAquariumCommand;
import com.example.demo.model.dto.AquariumDto;
import com.example.demo.service.AquariumService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/aquarium")
@RequiredArgsConstructor
@Slf4j
public class AquariumController {
    private final AquariumService aquariumService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity createAquarium(@RequestBody CreateaAquariumCommand aquarium) {
        Aquarium savedAquarium = aquariumService.save(aquarium);
        return new ResponseEntity(modelMapper.map(savedAquarium, AquariumDto.class), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity getAquariums() {
        List<AquariumDto> collect = aquariumService.getAll().stream()
            .map(aquarium -> modelMapper.map(aquarium, AquariumDto.class))
            .collect(Collectors.toList());

        return ResponseEntity.ok(collect);
    }
}
