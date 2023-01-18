package com.agleveratto.superhero.application.usecases;

import com.agleveratto.superhero.domain.usecases.DeleteSuperheroUseCase;
import org.springframework.stereotype.Component;

@Component
public class DeleteSuperheroUseCaseImpl implements DeleteSuperheroUseCase {
    @Override
    public int execute(Long id) {
        return 0;
    }
}
