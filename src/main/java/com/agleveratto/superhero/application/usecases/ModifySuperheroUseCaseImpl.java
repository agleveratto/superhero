package com.agleveratto.superhero.application.usecases;

import com.agleveratto.superhero.domain.usecases.FindSuperheroByIdUseCase;
import com.agleveratto.superhero.domain.usecases.ModifySuperheroUseCase;
import com.agleveratto.superhero.infrastructure.entities.Superhero;
import com.agleveratto.superhero.infrastructure.repositories.SuperheroRepository;
import org.springframework.stereotype.Component;

@Component
public class ModifySuperheroUseCaseImpl implements ModifySuperheroUseCase {

    private final FindSuperheroByIdUseCase findSuperheroByIdUseCase;
    private final SuperheroRepository repository;

    public ModifySuperheroUseCaseImpl(FindSuperheroByIdUseCase findSuperheroByIdUseCase, SuperheroRepository repository) {
        this.findSuperheroByIdUseCase = findSuperheroByIdUseCase;
        this.repository = repository;
    }

    @Override
    public int execute(Superhero superhero) {
        if(findSuperheroByIdUseCase.execute(superhero.getId()).isPresent()){
            return repository.updateSuperhero(superhero.getId(), superhero.getName());
        }
        return 0;
    }
}
