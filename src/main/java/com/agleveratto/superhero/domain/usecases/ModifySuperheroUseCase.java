package com.agleveratto.superhero.domain.usecases;

import com.agleveratto.superhero.infrastructure.entities.Superhero;

public interface ModifySuperheroUseCase {
    int execute(Superhero superhero);
}
