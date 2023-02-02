package com.agleveratto.superhero.application.services;

import com.agleveratto.superhero.application.exceptions.NotFoundException;
import com.agleveratto.superhero.domain.usecases.*;
import com.agleveratto.superhero.infrastructure.entities.Superhero;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuperheroService {
    public static final String SUPERHEROES_NOT_FOUND = "superheroes not found!";
    public static final String SUPERHERO_NOT_FOUND_BY_ID = "superhero not found by id ";
    public static final String SUPERHERO_MODIFIED = "superhero modified";
    public static final String SUPERHERO_DELETED = "superhero deleted";
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
        var superheroListCached = redisService.getAllSuperheroes();

        if (!superheroListCached.isEmpty()){
            logger.info("findAll Superheroes cached");
            return superheroListCached;
        }

        logger.info("findAll superheroes not cached, finding into database");
        var superheroes = findAllSuperheroUseCase.execute();

        if(superheroes.isEmpty())
            throw new NotFoundException(SUPERHEROES_NOT_FOUND);

        logger.info("caching superheroes for future requests");
        redisService.setAllSuperheroes(superheroes);

        return superheroes;
    }

    public Superhero findById(Long id) {
        var superheroCached = redisService.getSuperhero(id);

        if (superheroCached != null){
            logger.info("Superhero cached");
            return superheroCached;
        }

        logger.info("superhero not cached, finding into database");
        var optionalSuperhero = findSuperheroByIdUseCase.execute(id);

        if (optionalSuperhero.isEmpty())
            throw new NotFoundException(new StringBuilder().append(SUPERHERO_NOT_FOUND_BY_ID).append(id).toString());

        var superhero = optionalSuperhero.get();

        logger.info("caching superhero for future requests");
        redisService.setSuperhero(id, superhero);

        return superhero;
    }

    public List<Superhero> findByContains(String nameContains) {
        var superheroListCached = redisService.getSuperheroesByName(nameContains);

        if (!superheroListCached.isEmpty()){
            logger.info("Superheroes cached");
            return superheroListCached;
        }

        logger.info("superheroes not cached, finding into database");
        var superheroes = findSuperheroNameLikeUseCase.execute(nameContains);

        if (superheroes.isEmpty())
            throw new NotFoundException(new StringBuilder()
                    .append("superheroes not found that contains the word [")
                    .append(nameContains)
                    .append("] into their name")
                    .toString());

        logger.info("caching superhero for future requests");
        redisService.setSuperheroesByName(nameContains, superheroes);

        return superheroes;
    }

    public String update(Superhero superhero) {
        var rowsModified = modifySuperheroUseCase.execute(superhero);
        if (rowsModified == 0)
            throw new NotFoundException(new StringBuilder()
                    .append(SUPERHERO_NOT_FOUND_BY_ID)
                    .append(superhero.getId())
                    .toString());
        return SUPERHERO_MODIFIED;
    }

    public String delete(Long id) {
        try{
            deleteSuperheroUseCase.execute(id);
        } catch (EmptyResultDataAccessException exception) {
            throw new NotFoundException(new StringBuilder()
                    .append(SUPERHERO_NOT_FOUND_BY_ID)
                    .append(id)
                    .toString());
        }
        redisService.deleteKeyCached(id);
        return SUPERHERO_DELETED;
    }
}
