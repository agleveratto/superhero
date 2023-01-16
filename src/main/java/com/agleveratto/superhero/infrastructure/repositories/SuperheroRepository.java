package com.agleveratto.superhero.infrastructure.repositories;

import com.agleveratto.superhero.infrastructure.entities.Superhero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuperheroRepository extends JpaRepository<Superhero, Long> {
}
