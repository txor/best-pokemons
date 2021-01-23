package org.txor.bestpokemons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.txor.bestpokemons.domain.HeavyService;
import org.txor.bestpokemons.domain.PokemonApiClient;
import org.txor.bestpokemons.domain.PokemonApiDTOToPokemonDAOConverter;
import org.txor.bestpokemons.domain.PokemonDaoToPokemonConverter;
import org.txor.bestpokemons.domain.PokemonToPokemonDtoConverter;
import org.txor.bestpokemons.domain.RefreshDataService;
import org.txor.bestpokemons.infrastructure.PokemonApiClientRest;
import org.txor.bestpokemons.repository.PokemonRepository;

@Configuration
public class BestPokemonsConfiguration {

    @Autowired
    private PokemonRepository pokemonRepository;

    @Bean
    public PokemonToPokemonDtoConverter pokemonToPokemonDtoConverter() {
        return new PokemonToPokemonDtoConverter();
    }

    @Bean
    public PokemonDaoToPokemonConverter pokemonDaoToPokemonConverter() {
        return new PokemonDaoToPokemonConverter();
    }

    @Bean
    public PokemonApiDTOToPokemonDAOConverter pokemonApiDTOToPokemonDAOConverter() {
        return new PokemonApiDTOToPokemonDAOConverter();
    }

    @Bean
    public PokemonApiClient pokemonApiClient() {
        return new PokemonApiClientRest();
    }

    @Bean
    public HeavyService heavyService(RefreshDataService refreshDataService, PokemonRepository pokemonRepository, PokemonDaoToPokemonConverter pokemonDaoToPokemonConverter) {
        return new HeavyService(refreshDataService, pokemonRepository, pokemonDaoToPokemonConverter);
    }

    @Bean
    public RefreshDataService refreshDataService(PokemonRepository pokemonRepository, PokemonApiClient pokemonApiClient, PokemonApiDTOToPokemonDAOConverter pokemonApiDtoToPokemonDaoConverter) {
        return new RefreshDataService(pokemonRepository, pokemonApiClient, pokemonApiDtoToPokemonDaoConverter);
    }
}
