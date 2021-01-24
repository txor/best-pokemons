package org.txor.bestpokemons.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.txor.bestpokemons.domain.PokemonDAO;

import java.util.List;

public interface PokemonRepository extends CrudRepository<PokemonDAO, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM pokemon ORDER BY weight DESC LIMIT 5")
    List<PokemonDAO> getHeaviest();
}
