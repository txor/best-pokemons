package org.txor.bestpokemons.infrastructure;

import org.txor.bestpokemons.domain.PokemonApiClient;
import org.txor.bestpokemons.domain.PokemonApiDTO;

import java.util.ArrayList;
import java.util.List;

public class PokemonApiClientRest implements PokemonApiClient {

    @Override
    public List<PokemonApiDTO> getAllPokemons() {
        return new ArrayList<>();
    }
}
