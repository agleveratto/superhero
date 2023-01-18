package com.agleveratto.superhero.application.usecases;

import com.agleveratto.superhero.infrastructure.entities.Superhero;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ModifySuperheroUseCaseImplTest {

    @InjectMocks
    ModifySuperheroUseCaseImpl useCase;

    private static Superhero superhero;

    @BeforeAll
    static void setup(){
        superhero = new Superhero();
        superhero.setId(1L);
        superhero.setName("BATMAN");
    }

    @Test
    void execute_givenSuperheroModified_thenSaveIntoDB(){
        assertThat(useCase.execute(superhero)).isNotZero();
    }
}
