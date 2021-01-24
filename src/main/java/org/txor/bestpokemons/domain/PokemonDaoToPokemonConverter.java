package org.txor.bestpokemons.domain;

public class PokemonDaoToPokemonConverter {
    public Pokemon convert(PokemonDAO pokemonDAO) {
        Pokemon pokemon = new Pokemon();
        pokemon.setName(pokemonDAO.getName());
        pokemon.setUrl(pokemonDAO.getUrl());
        return pokemon;
    }
}
