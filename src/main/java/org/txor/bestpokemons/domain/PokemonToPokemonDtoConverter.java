package org.txor.bestpokemons.domain;

import org.txor.bestpokemons.api.PokemonDTO;

public class PokemonToPokemonDtoConverter {
    public PokemonDTO convert(Pokemon pokemon) {
        return new PokemonDTO();
    }
}