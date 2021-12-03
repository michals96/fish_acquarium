package com.example.demo.api;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;

import com.example.demo.model.Aquarium;
import com.example.demo.model.command.CreateaAquariumCommand;
import com.example.demo.model.dto.AquariumDto;
import com.example.demo.model.dto.FishDto;
import com.example.demo.service.AquariumService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/aquarium")
@RequiredArgsConstructor
@Slf4j
public class AquariumController {
    private final AquariumService aquariumService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity createAquarium(@RequestBody @Valid CreateaAquariumCommand aquarium) {
        Aquarium savedAquarium = aquariumService.save(aquarium);
        return new ResponseEntity(modelMapper.map(savedAquarium, AquariumDto.class), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}/{targetId}")
    @PreAuthorize("hasRole('ROLE_FISHERMAN')")
    public ResponseEntity moveFish(@PathVariable("id") final Long id, @PathVariable("targetId") final Long targetId) {
        return aquariumService.moveFish(id, targetId) ? new ResponseEntity(HttpStatus.NO_CONTENT)
            : new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity getAquariums() {
        return ResponseEntity.ok(aquariumService.getAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getFishesFromAquarium(@RequestParam final Long id) {
        List<FishDto> collect =
            aquariumService.getOne(id).getFishes()
                .stream().map(fish -> modelMapper.map(fish, FishDto.class)).collect(Collectors.toList());
        return ResponseEntity.ok(collect);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_SALESMAN')")
    public ResponseEntity remove(@PathVariable("id") final Long id) {
        return aquariumService.remove(id) ? new ResponseEntity(HttpStatus.NO_CONTENT)
            : new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /*
     1. dodawac rybki i przemieszczac rybki moze tylko user z rola ROLE_FISHERMAN
     2. usuwac akwarium moze tylko ziomek z rola ROLE_SALESMAN
     3. logika biznesowa do kontrolerow
     4. walidacje juz na poziomie modelu
     5. testy integracyjne
     6. testy jednostkowe
     */
}
