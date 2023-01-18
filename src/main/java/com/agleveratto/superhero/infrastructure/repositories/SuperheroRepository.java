package com.agleveratto.superhero.infrastructure.repositories;

import com.agleveratto.superhero.infrastructure.entities.Superhero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SuperheroRepository extends JpaRepository<Superhero, Long> {
    List<Superhero> findByNameContainingIgnoreCase(String value);

    @Modifying
    @Transactional
    @Query("update Superhero s set s.name = ?2 where s.id = ?1")
    int updateSuperheroSetNameForId(Long id, String name);
}
