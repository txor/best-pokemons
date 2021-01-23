package org.txor.bestpokemons.domain;

import org.txor.bestpokemons.repository.PokemonRepository;

import java.util.List;
import java.util.stream.Collectors;

public class HeavyService {

    private final RefreshDataService refreshDataService;
    private final PokemonRepository pokemonRepository;
    private final PokemonDaoToPokemonConverter pokemonDaoToPokemonConverter;

    public HeavyService(RefreshDataService refreshDataService, PokemonRepository pokemonRepository, PokemonDaoToPokemonConverter pokemonDaoToPokemonConverter) {
        this.refreshDataService = refreshDataService;
        this.pokemonRepository = pokemonRepository;
        this.pokemonDaoToPokemonConverter = pokemonDaoToPokemonConverter;
    }

    public List<Pokemon> getTopHeavy5Pokemons() {
        refreshDataService.refreshData();
        return pokemonRepository.getHeaviest().stream().map(pokemonDaoToPokemonConverter::convert).collect(Collectors.toList());
    }
}
