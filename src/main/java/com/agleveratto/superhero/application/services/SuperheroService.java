package com.agleveratto.superhero.application.services;

import com.agleveratto.superhero.application.exceptions.NotFoundException;
import com.agleveratto.superhero.domain.usecases.*;
import com.agleveratto.superhero.infrastructure.entities.Superhero;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SuperheroService {
    Logger logger = LoggerFactory.getLogger(SuperheroService.class);
    private final FindAllSuperheroUseCase findAllSuperheroUseCase;
    private final FindSuperheroByIdUseCase findSuperheroByIdUseCase;
    private final FindSuperheroNameLikeUseCase findSuperheroNameLikeUseCase;
    private final ModifySuperheroUseCase modifySuperheroUseCase;
    private final DeleteSuperheroUseCase deleteSuperheroUseCase;
    private final RedisService redisService;

    public SuperheroService(FindAllSuperheroUseCase findAllSuperheroUseCase, FindSuperheroByIdUseCase findSuperheroByIdUseCase,
                            FindSuperheroNameLikeUseCase findSuperheroNameLikeUseCase, ModifySuperheroUseCase modifySuperheroUseCase,
                            DeleteSuperheroUseCase deleteSuperheroUseCase, RedisService redisService) {
        this.findAllSuperheroUseCase = findAllSuperheroUseCase;
        this.findSuperheroByIdUseCase = findSuperheroByIdUseCase;
        this.findSuperheroNameLikeUseCase = findSuperheroNameLikeUseCase;
        this.modifySuperheroUseCase = modifySuperheroUseCase;
        this.deleteSuperheroUseCase = deleteSuperheroUseCase;
        this.redisService = redisService;
    }

    public List<Superhero> findAll() {
        List<Superhero> superheroListCached = redisService.getAllSuperheroes();

        if (!superheroListCached.isEmpty()){
            logger.info("findAll Superheroes cached");
            return superheroListCached;
        }

        logger.info("findAll superheroes not cached, finding into database");
        List<Superhero> superheroes = findAllSuperheroUseCase.execute();

        if(superheroes.isEmpty())
            throw new NotFoundException("superheroes not found!");

        logger.info("caching superheroes for future requests");
        redisService.setAllSuperheroes(superheroes);

        return superheroes;
    }

    public Superhero findById(Long id) {
        Superhero superheroCached = redisService.getSuperhero(id);

        if (superheroCached != null){
            logger.info("Superhero cached");
            return superheroCached;
        }

        logger.info("superhero not cached, finding into database");
        Optional<Superhero> optionalSuperhero = findSuperheroByIdUseCase.execute(id);

        if (optionalSuperhero.isEmpty())
            throw new NotFoundException("superhero not found by id " + id);

        Superhero superhero = optionalSuperhero.get();

        logger.info("caching superhero for future requests");
        redisService.setSuperhero(id, superhero);

        return superhero;
    }

    public List<Superhero> findByContains(String nameContains) {
        List<Superhero> superheroListCached = redisService.getSuperheroesByName(nameContains);

        if (superheroListCached != null){
            logger.info("Superheroes cached");
            return superheroListCached;
        }

        logger.info("superheroes not cached, finding into database");
        List<Superhero> superheroes = findSuperheroNameLikeUseCase.execute(nameContains);

        if (superheroes.isEmpty())
            throw new NotFoundException("superheroes not found that contains the word [" + nameContains + "] into their name");

        logger.info("caching superhero for future requests");
        redisService.setSuperheroesByName(nameContains, superheroes);

        return superheroes;
    }

    public String update(Superhero superhero) {
        int rowsModified = modifySuperheroUseCase.execute(superhero);
        if (rowsModified == 0)
            throw new NotFoundException("superhero not found by id " + superhero.getId());
        return "superhero modified";
    }

    public String delete(Long id) {
        try{
            deleteSuperheroUseCase.execute(id);
        } catch (EmptyResultDataAccessException exception) {
            throw new NotFoundException("superhero not found by id " + id);
        }
        redisService.deleteKeyCached(id);
        return "superhero deleted";
    }
}
