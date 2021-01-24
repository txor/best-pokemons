package org.txor.bestpokemons.domain;

import org.txor.bestpokemons.repository.PokemonRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ExperienceService {

    private final RefreshDataService refreshDataService;
    private final PokemonRepository pokemonRepository;
    private final PokemonDaoToPokemonConverter pokemonDaoToPokemonConverter;

    public ExperienceService(RefreshDataService refreshDataService, PokemonRepository pokemonRepository, PokemonDaoToPokemonConverter pokemonDaoToPokemonConverter) {
        this.refreshDataService = refreshDataService;
        this.pokemonRepository = pokemonRepository;
        this.pokemonDaoToPokemonConverter = pokemonDaoToPokemonConverter;
    }

    public List<Pokemon> getTopExperienced5Pokemons() {
        refreshDataService.refreshData();
        return pokemonRepository.getMostExperienced().stream().map(pokemonDaoToPokemonConverter::convert).collect(Collectors.toList());
    }
}
