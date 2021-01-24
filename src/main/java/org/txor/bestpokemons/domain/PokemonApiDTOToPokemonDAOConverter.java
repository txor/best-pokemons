package org.txor.bestpokemons.domain;

public class PokemonApiDTOToPokemonDAOConverter {
    public PokemonDAO convert(PokemonApiDTO pokemonApiDTO) {
        PokemonDAO pokemonDAO = new PokemonDAO();
        pokemonDAO.setName(pokemonApiDTO.getName());
        pokemonDAO.setUrl(pokemonApiDTO.getUrl());
        pokemonDAO.setWeight(Integer.parseInt(pokemonApiDTO.getWeight()));
        pokemonDAO.setHeight(Integer.parseInt(pokemonApiDTO.getHeight()));
        pokemonDAO.setExperience(Integer.parseInt(pokemonApiDTO.getExperience()));
        return pokemonDAO;
    }
}
