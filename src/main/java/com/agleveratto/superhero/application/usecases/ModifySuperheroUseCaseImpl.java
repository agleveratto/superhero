package com.agleveratto.superhero.application.usecases;

import com.agleveratto.superhero.domain.usecases.ModifySuperheroUseCase;
import com.agleveratto.superhero.infrastructure.entities.Superhero;
import org.springframework.stereotype.Component;

@Component
public class ModifySuperheroUseCaseImpl implements ModifySuperheroUseCase {

    @Override
    public int execute(Superhero superhero) {
        return 0;
    }
}
