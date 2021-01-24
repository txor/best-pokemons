package org.txor.bestpokemons.domain;

import org.txor.bestpokemons.repository.PokemonRepository;

import java.util.List;
import java.util.stream.Collectors;

public class HeightService {

    private final RefreshDataService refreshDataService;
    private final PokemonRepository pokemonRepository;
    private final PokemonDaoToPokemonConverter pokemonDaoToPokemonConverter;

    public HeightService(RefreshDataService refreshDataService, PokemonRepository pokemonRepository, PokemonDaoToPokemonConverter pokemonDaoToPokemonConverter) {
        this.refreshDataService = refreshDataService;
        this.pokemonRepository = pokemonRepository;
        this.pokemonDaoToPokemonConverter = pokemonDaoToPokemonConverter;
    }

    public List<Pokemon> getTopTallest5Pokemons() {
        refreshDataService.refreshData();
        return pokemonRepository.getTallest().stream().map(pokemonDaoToPokemonConverter::convert).collect(Collectors.toList());
    }
}
