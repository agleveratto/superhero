package com.agleveratto.superhero.application.usecases;

import com.agleveratto.superhero.domain.usecases.DeleteSuperheroUseCase;
import com.agleveratto.superhero.infrastructure.repositories.SuperheroRepository;
import org.springframework.stereotype.Component;

@Component
public class DeleteSuperheroUseCaseImpl implements DeleteSuperheroUseCase {

    private final SuperheroRepository repository;

    public DeleteSuperheroUseCaseImpl(SuperheroRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(Long id) {
        repository.deleteById(id);
    }
}
