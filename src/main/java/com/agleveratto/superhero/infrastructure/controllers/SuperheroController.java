package com.agleveratto.superhero.infrastructure.controllers;

import com.agleveratto.superhero.application.services.SuperheroService;
import com.agleveratto.superhero.infrastructure.entities.Superhero;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
public class SuperheroController {

    private final SuperheroService superheroService;

    public SuperheroController(SuperheroService superheroService) {
        this.superheroService = superheroService;
    }


    @GetMapping(value = "/")
    public ResponseEntity<List<Superhero>> findAll(){
        return ResponseEntity.ok(superheroService.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Superhero> findById(@PathVariable("id") Long id){
        return ResponseEntity.ok(superheroService.findById(id));
    }

    @GetMapping(value = "/name/{nameContains}")
    public ResponseEntity<List<Superhero>> findByContains(@PathVariable("nameContains") String nameContains){
        return ResponseEntity.ok(superheroService.findByContains(nameContains));
    }

    @PutMapping(value = "/")
    public ResponseEntity<String> update(@RequestBody Superhero superhero){
        return ResponseEntity.ok(superheroService.update(superhero));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        return ResponseEntity.ok(superheroService.delete(id));
    }

}
