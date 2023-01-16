package com.agleveratto.superhero.application.usecases;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class FindAllSuperheroUseCaseImplTest {

    @InjectMocks
    FindAllSuperheroUseCaseImpl useCase;

    @Test
    void execute_whenCallMethod_thenReturnEmptyList(){
        assertThat(useCase.execute()).isNotNull();
    }
}
