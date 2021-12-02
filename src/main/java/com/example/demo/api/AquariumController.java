package com.example.demo.api;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.example.demo.model.Aquarium;
import com.example.demo.model.Fish;
import com.example.demo.model.command.AddFishToAquariumCommand;
import com.example.demo.model.command.CreateaAquariumCommand;
import com.example.demo.model.dto.AquariumDto;
import com.example.demo.service.AquariumService;
import com.example.demo.service.FishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/aquarium")
@RequiredArgsConstructor
@Slf4j
public class AquariumController {
    private final AquariumService aquariumService;
    private final FishService fishService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity createAquarium(@RequestBody CreateaAquariumCommand aquarium) {
        Aquarium savedAquarium = aquariumService.save(aquarium);
        return new ResponseEntity(modelMapper.map(savedAquarium, AquariumDto.class), HttpStatus.CREATED);
    }

    @PostMapping(value = "/addfish")
    @Transactional
    public ResponseEntity addFishToAquarium(@RequestBody AddFishToAquariumCommand command) {
        Aquarium aquarium = aquariumService.findOne(command.getAquariumId());
        Fish fish = fishService.findOne(command.getFishId());

        aquarium.addFish(fish);

        return new ResponseEntity(modelMapper.map(aquarium, AquariumDto.class), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity getAquariums() {
        List<AquariumDto> collect = aquariumService.getAll().stream()
            .map(aquarium -> modelMapper.map(aquarium, AquariumDto.class))
            .collect(Collectors.toList());

        return ResponseEntity.ok(collect);
    }
}
