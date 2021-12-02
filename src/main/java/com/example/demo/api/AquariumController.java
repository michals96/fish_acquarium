package com.example.demo.api;

import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.model.Aquarium;
import com.example.demo.model.Fish;
import com.example.demo.model.command.MoveFishToAquariumCommand;
import com.example.demo.model.command.CreateaAquariumCommand;
import com.example.demo.model.dto.AquariumDto;
import com.example.demo.model.dto.FishDto;
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

    @PostMapping(value = "/movefish")
    @Transactional
    public ResponseEntity moveFish(@RequestBody MoveFishToAquariumCommand command) {
        Aquarium aquarium = aquariumService.findOne(command.getAquariumId());

        if(!aquarium.validateIfPossibleToAddFish()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        
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

    @GetMapping(value = "/fishes")
    public ResponseEntity getFishesFromAquarium(@RequestParam final Long id) {
        List<FishDto> collect =
            aquariumService.findOne(id).getFishes().stream().map(fish -> modelMapper.map(fish, FishDto.class)).collect(Collectors.toList());

        return ResponseEntity.ok(collect);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity remove(@PathVariable("id") final Long id) {
        List<Fish> fishes = aquariumService.findOne(id).getFishes();

        if(!fishes.isEmpty()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        aquariumService.remove(id);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
