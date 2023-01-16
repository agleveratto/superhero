package com.agleveratto.superhero.application.usecases;

import com.agleveratto.superhero.infrastructure.entities.Superhero;
import com.agleveratto.superhero.infrastructure.repositories.SuperheroRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindAllSuperheroUseCaseImplTest {

    @InjectMocks
    private FindAllSuperheroUseCaseImpl useCase;

    @Mock
    private SuperheroRepository repository;

    private static Superhero superhero;

    @BeforeAll
    static void setup(){
        superhero = new Superhero();
        superhero.setId(1L);
        superhero.setName("SUPERMAN");
    }

    @Test
    void execute_whenCallMethod_thenReturnEmptyList(){
        assertThat(useCase.execute()).isNotNull().isEmpty();
        verify(repository).findAll();
    }

    @Test
    void execute_whenCallMethod_thenReturnSuperheroList(){
        when(repository.findAll()).thenReturn(List.of(superhero));
        assertThat(useCase.execute()).isNotNull().containsExactly(superhero);
        verify(repository).findAll();
    }

}
