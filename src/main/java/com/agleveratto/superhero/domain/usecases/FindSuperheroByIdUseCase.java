package com.agleveratto.superhero.domain.usecases;

import com.agleveratto.superhero.infrastructure.entities.Superhero;

public interface FindSuperheroByIdUseCase {

    Superhero execute(Long id);
}
