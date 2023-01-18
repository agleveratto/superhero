package com.agleveratto.superhero.application.services;

import com.agleveratto.superhero.application.exceptions.NotFoundException;
import com.agleveratto.superhero.domain.usecases.FindAllSuperheroUseCase;
import com.agleveratto.superhero.domain.usecases.FindSuperheroByIdUseCase;
import com.agleveratto.superhero.infrastructure.entities.Superhero;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuperheroService {

    private final FindAllSuperheroUseCase findAllSuperheroUseCase;
    private final FindSuperheroByIdUseCase findSuperheroByIdUseCase;

    public SuperheroService(FindAllSuperheroUseCase findAllSuperheroUseCase, FindSuperheroByIdUseCase findSuperheroByIdUseCase) {
        this.findAllSuperheroUseCase = findAllSuperheroUseCase;
        this.findSuperheroByIdUseCase = findSuperheroByIdUseCase;
    }

    public List<Superhero> findAll() {
        List<Superhero> superheroes = findAllSuperheroUseCase.execute();
        if(superheroes.isEmpty())
            throw new NotFoundException("superheroes not found!");
        return superheroes;
    }

    public Superhero findById(Long id) {
        return findSuperheroByIdUseCase.execute(id).get();
    }
}
