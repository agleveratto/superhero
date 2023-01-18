package com.agleveratto.superhero.application.usecases;

import com.agleveratto.superhero.domain.usecases.FindSuperheroNameLikeUseCase;
import com.agleveratto.superhero.infrastructure.entities.Superhero;
import com.agleveratto.superhero.infrastructure.repositories.SuperheroRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindSuperheroNameLikeUseCaseImpl implements FindSuperheroNameLikeUseCase {

    private final SuperheroRepository repository;

    public FindSuperheroNameLikeUseCaseImpl(SuperheroRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Superhero> execute(String value) {
        return repository.findByNameContainingIgnoreCase(value);
    }
}
