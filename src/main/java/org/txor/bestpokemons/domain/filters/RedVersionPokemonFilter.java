package org.txor.bestpokemons.domain.filters;

import org.txor.bestpokemons.domain.PokemonApiDTO;
import org.txor.bestpokemons.domain.PokemonFilter;

import java.util.Arrays;

public class RedVersionPokemonFilter implements PokemonFilter {

    public static final String VERSION = "red";

    @Override
    public boolean filter(PokemonApiDTO pokemon) {
        return Arrays.stream(pokemon.getVersions()).anyMatch(VERSION::equalsIgnoreCase);
    }
}
