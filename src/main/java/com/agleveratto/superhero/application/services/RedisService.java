package com.agleveratto.superhero.application.services;

import com.agleveratto.superhero.infrastructure.entities.Superhero;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    public static final String FIND_ALL_KEY = "findAll";
    private final RedisTemplate<String, String> redisTemplate;
    private final Gson gson;

    public RedisService(RedisTemplate<String, String> redisTemplate, Gson gson) {
        this.redisTemplate = redisTemplate;
        this.gson = gson;
    }

    public void setSuperhero(final Long id, Superhero superhero){
        redisTemplate.opsForValue().set(String.valueOf(id), gson.toJson(superhero));
        redisTemplate.expire(String.valueOf(id), 1, TimeUnit.MINUTES);
    }

    public void setAllSuperheroes(List<Superhero> superheroes) {
        redisTemplate.opsForValue().set(FIND_ALL_KEY, gson.toJson(superheroes));
        redisTemplate.expire(FIND_ALL_KEY, 1, TimeUnit.MINUTES);
    }

    public void setSuperheroesByName(String name, List<Superhero> superheroes) {
        redisTemplate.opsForValue().set(name, gson.toJson(superheroes));
        redisTemplate.expire(name, 1, TimeUnit.MINUTES);
    }

    public List<Superhero> getAllSuperheroes() {
        return gson.fromJson(redisTemplate.opsForValue().get(FIND_ALL_KEY), new TypeToken<List<Superhero>>(){}.getType());
    }

    public Superhero getSuperhero(final Long id) {
        return gson.fromJson(redisTemplate.opsForValue().get(String.valueOf(id)), Superhero.class);
    }

    public List<Superhero> getSuperheroesByName(String name) {
        return gson.fromJson(redisTemplate.opsForValue().get(name), new TypeToken<List<Superhero>>(){}.getType());
    }
    public void deleteKeyCached(final Long id) {
        Superhero itemToDelete = getSuperhero(id);
        // here delete from getAllSuperheroes
        List<Superhero> getAllSuperheroesCached = getAllSuperheroes();

        if(getAllSuperheroesCached.stream().anyMatch(superhero -> superhero.equals(itemToDelete))) {
            getAllSuperheroesCached.remove(itemToDelete);
            // update findAll values
            setAllSuperheroes(getAllSuperheroesCached);
        }

        // here delete getSuperhero cache
        redisTemplate.delete(String.valueOf(id));
    }

}
