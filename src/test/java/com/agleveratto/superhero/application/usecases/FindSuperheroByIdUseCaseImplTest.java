package com.agleveratto.superhero.application.usecases;

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
public class FindSuperheroByIdUseCaseImplTest {

    @InjectMocks
    private FindSuperheroByIdUseCaseImpl findSuperheroByIdUseCase;
    @Mock
    private SuperheroRepository repository;

    private static Superhero superman;

    @BeforeAll
    static void setup(){
        superman = new Superhero();
        superman.setId(1L);
        superman.setName("SUPERMAN");
    }

    @Test
    void execute_givenAId_thenReturnSuperhero() {
        when(repository.findById(1L)).thenReturn(Optional.of(superman));
        assertThat(findSuperheroByIdUseCase.execute(1L)).contains(superman);
        verify(repository).findById(1L);
    }

}
