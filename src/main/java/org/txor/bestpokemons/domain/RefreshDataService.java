package org.txor.bestpokemons.domain;

import org.txor.bestpokemons.repository.PokemonRepository;

import java.util.List;
import java.util.stream.Collectors;

public class RefreshDataService {

    private final PokemonRepository pokemonRepository;
    private final PokemonApiClient pokemonApiClient;
    private final PokemonApiDTOToPokemonDAOConverter pokemonApiDtoToPokemonDaoConverter;

    public RefreshDataService(PokemonRepository pokemonRepository, PokemonApiClient pokemonApiClient, PokemonApiDTOToPokemonDAOConverter pokemonApiDtoToPokemonDaoConverter) {
        this.pokemonRepository = pokemonRepository;
        this.pokemonApiClient = pokemonApiClient;
        this.pokemonApiDtoToPokemonDaoConverter = pokemonApiDtoToPokemonDaoConverter;
    }

    public void refreshData() {
        List<PokemonDAO> apiPokemons = pokemonApiClient.getAllPokemons().stream().map(pokemonApiDtoToPokemonDaoConverter::convert).collect(Collectors.toList());
        pokemonRepository.saveAll(apiPokemons);
    }
}
