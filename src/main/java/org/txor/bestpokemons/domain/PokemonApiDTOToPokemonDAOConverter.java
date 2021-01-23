package org.txor.bestpokemons.domain;

public class PokemonApiDTOToPokemonDAOConverter {
    public PokemonDAO convert(PokemonApiDTO pokemonApiDTO) {
        return new PokemonDAO();
    }
}
