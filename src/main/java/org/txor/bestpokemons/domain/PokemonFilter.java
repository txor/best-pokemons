package org.txor.bestpokemons.domain;

public interface PokemonFilter {
    boolean filter(PokemonApiDTO pokemon);
}
