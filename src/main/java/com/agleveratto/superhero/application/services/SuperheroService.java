package com.agleveratto.superhero.application.services;

import com.agleveratto.superhero.domain.usecases.FindAllSuperheroUseCase;
import com.agleveratto.superhero.infrastructure.entities.Superhero;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuperheroService {

    private final FindAllSuperheroUseCase findAllSuperheroUseCase;

    public SuperheroService(FindAllSuperheroUseCase findAllSuperheroUseCase) {
        this.findAllSuperheroUseCase = findAllSuperheroUseCase;
    }

    public List<Superhero> findAll() {
        return findAllSuperheroUseCase.execute();
    }
}
