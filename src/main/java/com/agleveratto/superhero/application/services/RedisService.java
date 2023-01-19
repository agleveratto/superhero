package com.agleveratto.superhero.application.services;

import com.agleveratto.superhero.infrastructure.entities.Superhero;
import com.google.gson.Gson;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

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

    public Superhero getSuperhero(final Long id) {
        return gson.fromJson(redisTemplate.opsForValue().get(String.valueOf(id)), Superhero.class);
    }

    public void deleteKeyCached(final Long id) {
        redisTemplate.delete(String.valueOf(id));
    }
}
