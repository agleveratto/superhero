package com.agleveratto.superhero.application.services;

import com.agleveratto.superhero.application.exceptions.NotFoundException;
import com.agleveratto.superhero.domain.usecases.*;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SuperheroServiceTest {

    @InjectMocks
    SuperheroService superheroService;

    @Mock
    FindAllSuperheroUseCase findAllSuperheroUseCase;

    @Mock
    FindSuperheroByIdUseCase findSuperheroByIdUseCase;

    @Mock
    FindSuperheroNameLikeUseCase findSuperheroNameLikeUseCase;

    @Mock
    ModifySuperheroUseCase modifySuperheroUseCase;

    @Mock
    DeleteSuperheroUseCase deleteSuperheroUseCase;

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

    @Test
    void findById_givenId_thenThrowNotFoundException(){
        when(findSuperheroByIdUseCase.execute(2L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> superheroService.findById(2L)).isInstanceOf(NotFoundException.class);
        verify(findSuperheroByIdUseCase).execute(2L);
    }

    @Test
    void findByContains_givenString_thenReturnList(){
        when(findSuperheroNameLikeUseCase.execute("man")).thenReturn(List.of(superhero));
        assertThat(superheroService.findByContains("man")).isNotEmpty();
        verify(findSuperheroNameLikeUseCase).execute("man");
    }

    @Test
    void findByContains_givenAString_thenThrowNotFoundException() {
        when(findSuperheroNameLikeUseCase.execute("men")).thenReturn(List.of());
        assertThatThrownBy(() -> superheroService.findByContains("men")).isInstanceOf(NotFoundException.class);
        verify(findSuperheroNameLikeUseCase).execute("men");
    }

    @Test
    void update_givenSuperheroModified_thenApplyModifications(){
        when(modifySuperheroUseCase.execute(superhero)).thenReturn(1);
        assertThat(superheroService.update(superhero)).isNotZero();
        verify(modifySuperheroUseCase).execute(superhero);
    }

    @Test
    void update_givenSuperheroModified_thenThrowNotFoundException(){
        when(modifySuperheroUseCase.execute(superhero)).thenReturn(0);
        assertThatThrownBy(() -> superheroService.update(superhero)).isInstanceOf(NotFoundException.class);
        verify(modifySuperheroUseCase).execute(superhero);
    }

    @Test
    void delete_givenId_thenDeleteSuperhero(){
        superheroService.delete(1L);
        verify(deleteSuperheroUseCase).execute(1L);
    }
}
