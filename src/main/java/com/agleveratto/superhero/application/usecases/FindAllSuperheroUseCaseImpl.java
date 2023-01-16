package com.agleveratto.superhero.application.usecases;

import com.agleveratto.superhero.domain.usecases.FindAllSuperheroUseCase;
import com.agleveratto.superhero.infrastructure.entities.Superhero;
import com.agleveratto.superhero.infrastructure.repositories.SuperheroRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindAllSuperheroUseCaseImpl implements FindAllSuperheroUseCase {

    private final SuperheroRepository repository;

    public FindAllSuperheroUseCaseImpl(SuperheroRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Superhero> execute() {
        return repository.findAll();
    }
}
