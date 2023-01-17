package com.agleveratto.superhero.domain.usecases;

import com.agleveratto.superhero.infrastructure.entities.Superhero;

import java.util.List;

public interface FindSuperheroNameLikeUseCase {
    List<Superhero> execute();
}
