package com.agleveratto.superhero.application.usecases;

import com.agleveratto.superhero.domain.usecases.FindAllSuperheroUseCase;
import com.agleveratto.superhero.infrastructure.entities.Superhero;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindAllSuperheroUseCaseImpl implements FindAllSuperheroUseCase {
    @Override
    public List<Superhero> execute() {
        return null;
    }
}
