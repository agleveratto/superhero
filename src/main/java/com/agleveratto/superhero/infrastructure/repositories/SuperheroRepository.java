package com.agleveratto.superhero.infrastructure.repositories;

import com.agleveratto.superhero.infrastructure.entities.Superhero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuperheroRepository extends JpaRepository<Superhero, Long> {
    List<Superhero> findByNameLike(String value);

    @Modifying
    @Query("update superhero set superhero.name = ?2 where superhero.id = ?1")
    int updateSuperhero(Long id, String name);
}
