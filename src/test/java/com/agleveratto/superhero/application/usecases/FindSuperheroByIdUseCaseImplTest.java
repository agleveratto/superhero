package com.agleveratto.superhero.application.usecases;

import com.agleveratto.superhero.infrastructure.repositories.SuperheroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class FindSuperheroByIdUseCaseImplTest {

    @Mock
    private SuperheroRepository repository;

    @Test
    void execute_givenAId_thenReturnSuperhero() {
        assertThat(repository.findById(1L)).isNotNull();
    }
}
