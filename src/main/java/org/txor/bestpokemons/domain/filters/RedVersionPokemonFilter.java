package org.txor.bestpokemons.domain.filters;

import org.txor.bestpokemons.domain.PokemonApiDTO;
import org.txor.bestpokemons.domain.PokemonFilter;

public class RedVersionPokemonFilter implements PokemonFilter {

    public static final String VERSION = "red";

    @Override
    public boolean filter(PokemonApiDTO pokemon) {
        return pokemon.getVersions().stream().anyMatch(VERSION::equalsIgnoreCase);
    }
}
