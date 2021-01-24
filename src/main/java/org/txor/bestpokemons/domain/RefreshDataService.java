package org.txor.bestpokemons.domain;

import org.springframework.web.client.RestClientResponseException;
import org.txor.bestpokemons.repository.PokemonRepository;

import java.util.List;
import java.util.stream.Collectors;

public class RefreshDataService {

    private final PokemonRepository pokemonRepository;
    private final PokemonApiClient pokemonApiClient;
    private final PokemonFilter pokemonFilter;
    private final PokemonApiDTOToPokemonDAOConverter pokemonApiDtoToPokemonDaoConverter;
    private final LogService logService;

    public RefreshDataService(PokemonRepository pokemonRepository, PokemonApiClient pokemonApiClient, PokemonFilter pokemonFilter, PokemonApiDTOToPokemonDAOConverter pokemonApiDtoToPokemonDaoConverter, LogService logService) {
        this.pokemonRepository = pokemonRepository;
        this.pokemonApiClient = pokemonApiClient;
        this.pokemonFilter = pokemonFilter;
        this.pokemonApiDtoToPokemonDaoConverter = pokemonApiDtoToPokemonDaoConverter;
        this.logService = logService;
    }

    public void refreshData() {
        try {
            List<PokemonDAO> pokemons = pokemonApiClient.getAllPokemons().stream()
                    .filter(pokemonFilter::filter)
                    .map(pokemonApiDtoToPokemonDaoConverter::convert).collect(Collectors.toList());
            pokemonRepository.saveAll(pokemons);
        } catch (RestClientResponseException e) {
            logService.log(e);
        }
    }
}
