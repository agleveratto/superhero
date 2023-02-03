package com.agleveratto.superhero.infrastructure.controllers;

import com.agleveratto.superhero.application.services.SuperheroService;
import com.agleveratto.superhero.domain.annotations.ExecutionTime;
import com.agleveratto.superhero.infrastructure.entities.Superhero;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SuperheroController {

    private final SuperheroService superheroService;

    public SuperheroController(SuperheroService superheroService) {
        this.superheroService = superheroService;
    }

    @ExecutionTime
    @GetMapping(value = "/")
    public ResponseEntity<List<Superhero>> findAll(){
        return ResponseEntity.ok(superheroService.findAll());
    }

    @ExecutionTime
    @GetMapping(value = "/{id}")
    public ResponseEntity<Superhero> findById(@PathVariable("id") Long id){
        return ResponseEntity.ok(superheroService.findById(id));
    }

    @ExecutionTime
    @GetMapping(value = "/name/{nameContains}")
    public ResponseEntity<List<Superhero>> findByContains(@PathVariable("nameContains") String nameContains){
        return ResponseEntity.ok(superheroService.findByContains(nameContains));
    }

    @ExecutionTime
    @PutMapping(value = "/")
    public ResponseEntity<String> update(@RequestBody Superhero superhero){
        return ResponseEntity.ok(superheroService.update(superhero));
    }

    @ExecutionTime
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        return ResponseEntity.ok(superheroService.delete(id));
    }

}
