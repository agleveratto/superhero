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

    @Mock
    RedisService redisService;

    private static Superhero superhero;

    @BeforeAll
    static void setup(){
        superhero = new Superhero();
        superhero.setId(1L);
        superhero.setName("SUPERMAN");
    }

    @Test
    void findAll_whenCallMethod_thenReturnList(){
        when(redisService.getAllSuperheroes()).thenReturn(List.of());
        when(findAllSuperheroUseCase.execute()).thenReturn(List.of(superhero));
        assertThat(superheroService.findAll()).isNotEmpty().containsExactly(superhero);
        verify(redisService).getAllSuperheroes();
        verify(findAllSuperheroUseCase).execute();
    }

    @Test
    void findAll_whenCallMethod_thenThrowNotFoundException(){
        when(redisService.getAllSuperheroes()).thenReturn(List.of());
        when(findAllSuperheroUseCase.execute()).thenReturn(List.of());
        assertThatThrownBy(() -> superheroService.findAll()).isInstanceOf(NotFoundException.class);
        verify(redisService).getAllSuperheroes();
        verify(findAllSuperheroUseCase).execute();
    }

    @Test
    void findAll_whenCallMethod_thenReturnCachedList(){
        when(redisService.getAllSuperheroes()).thenReturn(List.of(superhero));
        assertThat(superheroService.findAll()).isNotEmpty().containsExactly(superhero);
        verify(redisService).getAllSuperheroes();
        verify(findAllSuperheroUseCase, never()).execute();
    }

    @Test
    void findById_givenId_thenReturnSuperhero(){
        when(redisService.getSuperhero(1L)).thenReturn(null);
        when(findSuperheroByIdUseCase.execute(1L)).thenReturn(Optional.of(superhero));
        assertThat(superheroService.findById(1L)).isNotNull().isEqualTo(superhero);
        verify(redisService).getSuperhero(1L);
        verify(findSuperheroByIdUseCase).execute(1L);
    }

    @Test
    void findById_givenId_thenThrowNotFoundException(){
        when(redisService.getSuperhero(2L)).thenReturn(null);
        when(findSuperheroByIdUseCase.execute(2L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> superheroService.findById(2L)).isInstanceOf(NotFoundException.class);
        verify(redisService).getSuperhero(2L);
        verify(findSuperheroByIdUseCase).execute(2L);
    }

    @Test
    void findById_givenId_thenReturnCachedSuperhero(){
        when(redisService.getSuperhero(1L)).thenReturn(superhero);
        assertThat(superheroService.findById(1L)).isNotNull().isEqualTo(superhero);
        verify(redisService).getSuperhero(1L);
        verify(findSuperheroByIdUseCase, never()).execute(1L);
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
        assertThat(superheroService.update(superhero)).isEqualTo("superhero modified");
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

    @Test
    void delete_givenNotExistId_thenThrowNotFoundException(){
        Superhero superheroNotExisted = new Superhero();
        superheroNotExisted.setId(2L);
        superheroNotExisted.setName("betman");
        assertThatThrownBy(() -> superheroService.update(superheroNotExisted)).isInstanceOf(NotFoundException.class);
    }
}
