package org.txor.bestpokemons.domain;

public class PokemonToPokemonDtoConverter {
    public PokemonDTO convert(Pokemon pokemon) {
        PokemonDTO pokemonDTO = new PokemonDTO();
        pokemonDTO.setName(pokemon.getName());
        pokemonDTO.setUrl(pokemon.getUrl());
        return pokemonDTO;
    }
}