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
public class FindSuperheroNameLikeUseCaseImplTest {

    @InjectMocks
    private FindSuperheroNameLikeUseCaseImpl findSuperheroNameLikeUseCase;

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
    void execute_givenARandomString_thenReturnEmptyList() {
        assertThat(findSuperheroNameLikeUseCase.execute("bat")).isEmpty();
        verify(repository).findByNameLike("bat");
    }

    @Test
    void execute_givenAString_thenReturnSuperheroList() {
        when(repository.findByNameLike("man")).thenReturn(List.of(superhero));
        assertThat(findSuperheroNameLikeUseCase.execute("man")).containsExactly(superhero);
        verify(repository).findByNameLike("man");
    }

}
