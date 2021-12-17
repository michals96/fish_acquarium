package com.example.demo.api;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;

import com.example.demo.model.Fish;
import com.example.demo.model.command.CreateFishCommand;
import com.example.demo.model.dto.FishDto;
import com.example.demo.service.AquariumService;
import com.example.demo.service.FishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/fish")
@RequiredArgsConstructor
@Slf4j
class FishController {
    private final FishService fishService;
    private final AquariumService aquariumService;
    private final ModelMapper modelMapper;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_FISHERMAN')")
    public ResponseEntity createFish(@RequestBody @Valid CreateFishCommand fish) {
        Fish savedFish = fishService.save(fish);
        return savedFish != null ? new ResponseEntity(modelMapper.map(savedFish, FishDto.class), HttpStatus.CREATED)
            : new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity getFish() { //dodac paginacje
        List<FishDto> collect = fishService.getAll().stream()
            .map(fish -> modelMapper.map(fish, FishDto.class))
            .collect(Collectors.toList());

        return ResponseEntity.ok(collect);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getFishFromAquarium(@RequestParam final Long id) {
        List<FishDto> collect =
            aquariumService.getOne(id).getFish()
                .stream().map(fish -> modelMapper.map(fish, FishDto.class)).collect(Collectors.toList());
        return ResponseEntity.ok(collect);
    }
}