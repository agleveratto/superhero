package com.agleveratto.superhero.application.services;

import com.agleveratto.superhero.application.exceptions.NotFoundException;
import com.agleveratto.superhero.domain.usecases.FindAllSuperheroUseCase;
import com.agleveratto.superhero.domain.usecases.FindSuperheroByIdUseCase;
import com.agleveratto.superhero.infrastructure.entities.Superhero;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SuperheroServiceTest {

    @InjectMocks
    SuperheroService superheroService;

    @Mock
    FindAllSuperheroUseCase findAllSuperheroUseCase;

    @Mock
    FindSuperheroByIdUseCase findSuperheroByIdUseCase;

    private static Superhero superhero;

    @BeforeAll
    static void setup(){
        superhero = new Superhero();
        superhero.setId(1L);
        superhero.setName("SUPERMAN");
    }

    @Test
    void findAll_whenCallMethod_thenReturnList(){
        when(findAllSuperheroUseCase.execute()).thenReturn(List.of(superhero));
        assertThat(superheroService.findAll()).isNotEmpty();
        verify(findAllSuperheroUseCase).execute();
    }

    @Test
    void findAll_whenCallMethod_thenThrowNotFoundException(){
        when(findAllSuperheroUseCase.execute()).thenReturn(List.of());
        assertThatThrownBy(() -> superheroService.findAll()).isInstanceOf(NotFoundException.class);
        verify(findAllSuperheroUseCase).execute();
    }

    @Test
    void findById_givenId_thenReturnSuperhero(){
        when(findSuperheroByIdUseCase.execute(1L)).thenReturn(Optional.of(superhero));
        assertThat(superheroService.findById(1L)).isNotNull();
        verify(findSuperheroByIdUseCase).execute(1L);
    }
}
