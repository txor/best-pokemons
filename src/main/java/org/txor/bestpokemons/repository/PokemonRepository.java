package org.txor.bestpokemons.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.txor.bestpokemons.domain.PokemonDAO;

import java.util.List;

public interface PokemonRepository extends PagingAndSortingRepository<PokemonDAO, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM pokemon ORDER BY weight ASC LIMIT 5")
    List<PokemonDAO> getHeaviest();
}
