package com.agleveratto.superhero.application.usecases;

import com.agleveratto.superhero.domain.usecases.ModifySuperheroUseCase;
import com.agleveratto.superhero.infrastructure.entities.Superhero;
import com.agleveratto.superhero.infrastructure.repositories.SuperheroRepository;
import org.springframework.stereotype.Component;

@Component
public class ModifySuperheroUseCaseImpl implements ModifySuperheroUseCase {

    private final SuperheroRepository repository;

    public ModifySuperheroUseCaseImpl(SuperheroRepository repository) {
        this.repository = repository;
    }

    @Override
    public int execute(Superhero superhero) {
        return repository.updateSuperhero(superhero.getId(), superhero.getName());
    }
}
