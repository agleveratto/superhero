package com.agleveratto.superhero.application.usecases;

import com.agleveratto.superhero.infrastructure.repositories.SuperheroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class FindAllSuperheroUseCaseImplTest {

    @InjectMocks
    FindAllSuperheroUseCaseImpl useCase;

    @Mock
    SuperheroRepository repository;

    @Test
    void execute_whenCallMethod_thenReturnEmptyList(){
        assertThat(useCase.execute()).isNotNull().isEmpty();
        verify(repository).findAll();
    }
}
