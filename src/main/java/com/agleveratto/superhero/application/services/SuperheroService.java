package com.agleveratto.superhero.application.services;

import com.agleveratto.superhero.infrastructure.entities.Superhero;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SuperheroService {

    public List<Superhero> findAll() {
        return new ArrayList<>();
    }
}
