package com.agleveratto.superhero.domain.usecases;

import com.agleveratto.superhero.infrastructure.entities.Superhero;

import java.util.Optional;

public interface FindSuperheroByIdUseCase {

    Optional<Superhero> execute(Long id);
}
