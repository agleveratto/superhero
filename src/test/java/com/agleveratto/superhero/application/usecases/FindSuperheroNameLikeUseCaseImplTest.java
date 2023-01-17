package com.agleveratto.superhero.application.usecases;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class FindSuperheroNameLikeUseCaseImplTest {

    @InjectMocks
    private FindSuperheroNameLikeUseCaseImpl findSuperheroNameLikeUseCase;

    @Test
    void execute_givenAString_thenReturnEmptyList() {
        assertThat(findSuperheroNameLikeUseCase.execute()).isEmpty();
    }

}
