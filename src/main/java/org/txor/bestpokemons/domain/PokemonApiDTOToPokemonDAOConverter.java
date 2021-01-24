package org.txor.bestpokemons.domain;

public class PokemonApiDTOToPokemonDAOConverter {
    public PokemonDAO convert(PokemonApiDTO pokemonApiDTO) {
        PokemonDAO pokemonDAO = new PokemonDAO();
        pokemonDAO.setName(pokemonApiDTO.getName());
        pokemonDAO.setUrl(pokemonApiDTO.getUrl());
        pokemonDAO.setWeight(pokemonApiDTO.getWeight());
        pokemonDAO.setHeight(pokemonApiDTO.getHeight());
        pokemonDAO.setExperience(pokemonApiDTO.getExperience());
        return pokemonDAO;
    }
}
