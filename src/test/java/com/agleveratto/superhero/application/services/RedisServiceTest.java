package com.agleveratto.superhero.application.services;

import com.agleveratto.superhero.infrastructure.entities.Superhero;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RedisServiceTest {

    @InjectMocks
    RedisService redisService;

    @Mock
    static RedisTemplate<String, String> redisTemplate;

    @Mock
    private ValueOperations valueOperations;

    private final Gson gson = new GsonBuilder().create();

    static Superhero superhero;

    @BeforeAll
    static void setup(){
        superhero = new Superhero();
        superhero.setId(1L);
        superhero.setName("SUPERMAN");
    }

    @BeforeEach
    void initGson(){
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        ReflectionTestUtils.setField(redisService, "gson", gson);
    }

    @Test
    void setSuperhero_givenIdAndSuperhero_thenSaveInCache(){
        redisService.setSuperhero(superhero.getId(), superhero);
        verify(redisTemplate).opsForValue();
        verify(redisTemplate).expire(String.valueOf(superhero.getId()),1, TimeUnit.MINUTES);
    }

    @Test
    void setAllSuperheroes_givenSuperheroList_thenSaveInCache(){
        redisService.setAllSuperheroes(List.of(superhero));
        verify(redisTemplate).opsForValue();
        verify(redisTemplate).expire("findAll",1, TimeUnit.MINUTES);
    }

    @Test
    void setSuperheroesByName_givenStringAndSuperheroList_thenSaveInCache(){
        redisService.setSuperheroesByName("man", List.of(superhero));
        verify(redisTemplate).opsForValue();
        verify(redisTemplate).expire("man",1, TimeUnit.MINUTES);
    }

    @Test
    void getAllSuperheroes_givenKey_thenReturnCachedList(){
        String superheroJson = gson.toJson(List.of(superhero));
        when(valueOperations.get("findAll")).thenReturn(superheroJson);
        assertThat(redisService.getAllSuperheroes())
                .isNotEmpty()
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(superhero);
        verify(redisTemplate).opsForValue();
        verify(valueOperations).get("findAll");
    }

    @Test
    void getAllSuperheroes_givenKey_thenReturnNull(){
        when(valueOperations.get("findAll")).thenReturn(null);
        assertThat(redisService.getAllSuperheroes())
                .isNull();
        verify(redisTemplate).opsForValue();
        verify(valueOperations).get("findAll");
    }

    @Test
    void getSuperhero_giveId_thenReturnCached(){
        String superheroJson = gson.toJson(superhero);
        when(valueOperations.get(superhero.getId().toString())).thenReturn(superheroJson);
        assertThat(redisService.getSuperhero(superhero.getId()))
                .isNotNull().usingRecursiveComparison().isEqualTo(superhero);
        verify(redisTemplate).opsForValue();
        verify(valueOperations).get(superhero.getId().toString());
    }

    @Test
    void getSuperhero_giveId_thenNull(){
        when(valueOperations.get("2")).thenReturn(null);
        assertThat(redisService.getSuperhero(2L))
                .isNull();
        verify(redisTemplate).opsForValue();
        verify(valueOperations).get("2");
    }

    @Test
    void getSuperheroesByName_givenString_thenReturnCached(){
        String superheroJson = gson.toJson(List.of(superhero));
        when(valueOperations.get("man")).thenReturn(superheroJson);
        assertThat(redisService.getSuperheroesByName("man"))
                .isNotEmpty()
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(superhero);
        verify(redisTemplate).opsForValue();
        verify(valueOperations).get("man");
    }

    @Test
    void getSuperheroesByName_givenString_thenNull(){
        when(valueOperations.get("men")).thenReturn(null);
        assertThat(redisService.getSuperheroesByName("men"))
                .isNull();
        verify(redisTemplate).opsForValue();
        verify(valueOperations).get("men");
    }

    @Test
    void deleteKeyCached_givenId_deleteInAllCaches(){
        String superheroJson = gson.toJson(superhero);
        String superheroListJson = gson.toJson(List.of(superhero));
        when(valueOperations.get(superhero.getId().toString())).thenReturn(superheroJson);
        when(valueOperations.get("findAll")).thenReturn(superheroListJson);

        redisService.deleteKeyCached(superhero.getId());

        verify(valueOperations, times(2)).get(anyString());
        verify(redisTemplate).delete(String.valueOf(superhero.getId()));
    }
}
