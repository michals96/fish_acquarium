package com.example.demo.api;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;

import com.example.demo.model.Fish;
import com.example.demo.model.command.CreateFishCommand;
import com.example.demo.model.dto.FishDto;
import com.example.demo.service.FishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/fish")
@RequiredArgsConstructor
@Slf4j
public class FishController {
    private final FishService fishService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity createFish(@RequestBody @Valid CreateFishCommand fish) {
        Fish savedFish = fishService.save(fish);
        return new ResponseEntity(modelMapper.map(savedFish, FishDto.class), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity getFishes() {
        List<FishDto> collect = fishService.getAll().stream()
            .map(fish -> modelMapper.map(fish, FishDto.class))
            .collect(Collectors.toList());

        return ResponseEntity.ok(collect);
    }
}
/*
+ Mozliwosc stworzenia akwarium (id, nazwa, pojemnosc)
2. Dodawanie rybek do akwarium (kazda ryba ma id nazwa gatunek cena)
3. Rybke mozna dodac do akawrium nie moze istniec rybka niedoadna do akwarium
4. wypisywanie stanu akwarium (jakie akwarium rybki wartosc w srodku)
5. usuwanie akwarium, ale jesli nie ma w nim rybek
6. nie mozna usuwac rybek, mozemy przenosic z pomiedzy akwarium
7. Na kazdy 1l akwarium moze byc 1 rybka
 */