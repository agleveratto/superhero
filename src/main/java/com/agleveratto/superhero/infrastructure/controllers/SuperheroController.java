package com.agleveratto.superhero.infrastructure.controllers;

import com.agleveratto.superhero.domain.usecases.FindAllSuperheroUseCase;
import com.agleveratto.superhero.infrastructure.entities.Superhero;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
public class SuperheroController {

    private final FindAllSuperheroUseCase findAllSuperheroUseCase;

    public SuperheroController(FindAllSuperheroUseCase findAllSuperheroUseCase) {
        this.findAllSuperheroUseCase = findAllSuperheroUseCase;
    }

    @GetMapping("/")
    public List<Superhero> getAll(){
        return findAllSuperheroUseCase.execute();
    }
}
