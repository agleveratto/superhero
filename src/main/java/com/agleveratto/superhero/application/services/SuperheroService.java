package com.agleveratto.superhero.application.services;

import com.agleveratto.superhero.application.exceptions.NotFoundException;
import com.agleveratto.superhero.domain.usecases.*;
import com.agleveratto.superhero.infrastructure.entities.Superhero;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SuperheroService {

    private final FindAllSuperheroUseCase findAllSuperheroUseCase;
    private final FindSuperheroByIdUseCase findSuperheroByIdUseCase;
    private final FindSuperheroNameLikeUseCase findSuperheroNameLikeUseCase;
    private final ModifySuperheroUseCase modifySuperheroUseCase;
    private final DeleteSuperheroUseCase deleteSuperheroUseCase;

    public SuperheroService(FindAllSuperheroUseCase findAllSuperheroUseCase, FindSuperheroByIdUseCase findSuperheroByIdUseCase,
                            FindSuperheroNameLikeUseCase findSuperheroNameLikeUseCase, ModifySuperheroUseCase modifySuperheroUseCase,
                            DeleteSuperheroUseCase deleteSuperheroUseCase) {
        this.findAllSuperheroUseCase = findAllSuperheroUseCase;
        this.findSuperheroByIdUseCase = findSuperheroByIdUseCase;
        this.findSuperheroNameLikeUseCase = findSuperheroNameLikeUseCase;
        this.modifySuperheroUseCase = modifySuperheroUseCase;
        this.deleteSuperheroUseCase = deleteSuperheroUseCase;
    }

    public List<Superhero> findAll() {
        List<Superhero> superheroes = findAllSuperheroUseCase.execute();
        if(superheroes.isEmpty())
            throw new NotFoundException("superheroes not found!");
        return superheroes;
    }

    public Superhero findById(Long id) {
        Optional<Superhero> optionalSuperhero = findSuperheroByIdUseCase.execute(id);
        if (optionalSuperhero.isEmpty())
            throw new NotFoundException("superhero not found by id " + id);
        return optionalSuperhero.get();
    }

    public List<Superhero> findByContains(String nameContains) {
        List<Superhero> superheroes = findSuperheroNameLikeUseCase.execute(nameContains);
        if (superheroes.isEmpty())
            throw new NotFoundException("superheroes not found that contains the word [" + nameContains + "] into their name");
        return superheroes;
    }

    public int update(Superhero superhero) {
        int rowsModified = modifySuperheroUseCase.execute(superhero);
        if (rowsModified == 0)
            throw new NotFoundException("superhero not found by id " + superhero.getId());
        return rowsModified;
    }

    public void delete(Long id) {
        try{
            deleteSuperheroUseCase.execute(id);
        } catch (EmptyResultDataAccessException exception) {
            throw new NotFoundException(exception.getMessage());
        }
    }
}
