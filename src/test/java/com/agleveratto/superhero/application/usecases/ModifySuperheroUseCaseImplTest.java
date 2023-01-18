package com.agleveratto.superhero.application.usecases;

import com.agleveratto.superhero.domain.usecases.FindSuperheroByIdUseCase;
import com.agleveratto.superhero.infrastructure.entities.Superhero;
import com.agleveratto.superhero.infrastructure.repositories.SuperheroRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ModifySuperheroUseCaseImplTest {

    @InjectMocks
    ModifySuperheroUseCaseImpl useCase;

    @Mock
    SuperheroRepository repository;

    @Mock
    FindSuperheroByIdUseCase findSuperheroByIdUseCase;

    private static Superhero superhero;

    @BeforeAll
    static void setup(){
        superhero = new Superhero();
        superhero.setId(1L);
        superhero.setName("BATMAN");
    }

    @Test
    void execute_givenSuperheroModified_thenSaveIntoDB(){
        when(findSuperheroByIdUseCase.execute(superhero.getId())).thenReturn(Optional.of(superhero));
        when(repository.updateSuperhero(superhero.getId(), superhero.getName())).thenReturn(1);
        assertThat(useCase.execute(superhero)).isNotZero();
        verify(findSuperheroByIdUseCase).execute(superhero.getId());
        verify(repository).updateSuperhero(superhero.getId(), superhero.getName());
    }
}
