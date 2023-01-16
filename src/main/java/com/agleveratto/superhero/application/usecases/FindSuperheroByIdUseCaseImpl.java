package com.agleveratto.superhero.application.usecases;

import com.agleveratto.superhero.application.exceptions.NotFoundException;
import com.agleveratto.superhero.domain.usecases.FindSuperheroByIdUseCase;
import com.agleveratto.superhero.infrastructure.entities.Superhero;
import com.agleveratto.superhero.infrastructure.repositories.SuperheroRepository;
import org.springframework.stereotype.Component;

@Component
public class FindSuperheroByIdUseCaseImpl implements FindSuperheroByIdUseCase {

    private final SuperheroRepository repository;

    public FindSuperheroByIdUseCaseImpl(SuperheroRepository repository) {
        this.repository = repository;
    }

    @Override
    public Superhero execute(Long id) {
        return repository.findById(id).orElseThrow(NotFoundException::new);
    }
}
