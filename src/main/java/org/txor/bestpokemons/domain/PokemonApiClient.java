package org.txor.bestpokemons.domain;

import java.util.List;

public interface PokemonApiClient {
    List<PokemonApiDTO> getAllPokemons();
}
