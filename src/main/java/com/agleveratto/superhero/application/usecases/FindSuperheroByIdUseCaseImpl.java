package com.agleveratto.superhero.application.usecases;

import com.agleveratto.superhero.domain.usecases.FindSuperheroByIdUseCase;
import com.agleveratto.superhero.infrastructure.entities.Superhero;
import com.agleveratto.superhero.infrastructure.repositories.SuperheroRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FindSuperheroByIdUseCaseImpl implements FindSuperheroByIdUseCase {

    private final SuperheroRepository repository;

    public FindSuperheroByIdUseCaseImpl(SuperheroRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Superhero> execute(Long id) {
        return repository.findById(id);
    }
}
