package com.agleveratto.superhero.application.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class SuperheroServiceTest {

    @InjectMocks
    SuperheroService superheroService;

    @Test
    void findAll_whenCallMethod_thenReturnList(){
        assertThat(superheroService.findAll()).isNotEmpty();
    }
}
