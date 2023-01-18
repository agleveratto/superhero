package com.agleveratto.superhero.application.services;

import com.agleveratto.superhero.application.exceptions.NotFoundException;
import com.agleveratto.superhero.domain.usecases.FindAllSuperheroUseCase;
import com.agleveratto.superhero.domain.usecases.FindSuperheroByIdUseCase;
import com.agleveratto.superhero.infrastructure.entities.Superhero;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        Optional<Superhero> optionalSuperhero = findSuperheroByIdUseCase.execute(id);
        if (optionalSuperhero.isEmpty())
            throw new NotFoundException("superhero not found by id " + id);
        return optionalSuperhero.get();
    }

    public List<Superhero> findByContains(String nameContains) {
        return new ArrayList<>();
    }
}
