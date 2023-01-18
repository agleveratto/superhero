package com.agleveratto.superhero.application.usecases;

import com.agleveratto.superhero.infrastructure.repositories.SuperheroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteSuperheroUseCaseImplTest {

    @InjectMocks
    DeleteSuperheroUseCaseImpl deleteSuperheroUseCase;

    @Mock
    SuperheroRepository repository;

    @Test
    void execute_givenId_thenDeleteSuperhero(){
        deleteSuperheroUseCase.execute(1L);
        verify(repository).deleteById(1L);
    }
}
