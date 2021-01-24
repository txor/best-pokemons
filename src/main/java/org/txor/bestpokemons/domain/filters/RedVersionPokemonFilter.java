package org.txor.bestpokemons.domain.filters;

import org.txor.bestpokemons.domain.PokemonApiDTO;
import org.txor.bestpokemons.domain.PokemonFilter;

public class RedVersionPokemonFilter implements PokemonFilter {

    @Override
    public boolean filter(PokemonApiDTO pokemon) {
        return false;
    }
}
