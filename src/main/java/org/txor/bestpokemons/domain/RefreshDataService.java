package org.txor.bestpokemons.domain;

import org.txor.bestpokemons.repository.PokemonRepository;

import java.util.List;
import java.util.stream.Collectors;

public class RefreshDataService {

    private final PokemonRepository pokemonRepository;
    private final PokemonApiClient pokemonApiClient;
    private final PokemonFilter pokemonFilter;
    private final PokemonApiDTOToPokemonDAOConverter pokemonApiDtoToPokemonDaoConverter;

    public RefreshDataService(PokemonRepository pokemonRepository, PokemonApiClient pokemonApiClient, PokemonFilter pokemonFilter, PokemonApiDTOToPokemonDAOConverter pokemonApiDtoToPokemonDaoConverter) {
        this.pokemonRepository = pokemonRepository;
        this.pokemonApiClient = pokemonApiClient;
        this.pokemonFilter = pokemonFilter;
        this.pokemonApiDtoToPokemonDaoConverter = pokemonApiDtoToPokemonDaoConverter;
    }

    public void refreshData() {
        List<PokemonDAO> pokemons = pokemonApiClient.getAllPokemons().stream()
                .filter(pokemonFilter::filter)
                .map(pokemonApiDtoToPokemonDaoConverter::convert).collect(Collectors.toList());
        pokemonRepository.saveAll(pokemons);
    }
}
