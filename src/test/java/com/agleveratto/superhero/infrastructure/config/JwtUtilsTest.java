package com.agleveratto.superhero.infrastructure.config;

import io.jsonwebtoken.MalformedJwtException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class JwtUtilsTest {

    @InjectMocks
    JwtUtils jwtUtils;

    @Test
    void extractUserName(){
        assertThatThrownBy(() -> jwtUtils.extractUserName("a")).isInstanceOf(MalformedJwtException.class);
    }

    @Test
    void extractExpiration(){
        assertThatThrownBy(() -> jwtUtils.extractExpiration("a")).isInstanceOf(MalformedJwtException.class);
    }
}
