package org.txor.bestpokemons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.txor.bestpokemons.domain.ExperienceService;
import org.txor.bestpokemons.domain.HeavyService;
import org.txor.bestpokemons.domain.HeightService;
import org.txor.bestpokemons.domain.PokemonApiClient;
import org.txor.bestpokemons.domain.PokemonApiDTOToPokemonDAOConverter;
import org.txor.bestpokemons.domain.PokemonDaoToPokemonConverter;
import org.txor.bestpokemons.domain.PokemonFilter;
import org.txor.bestpokemons.domain.PokemonToPokemonDtoConverter;
import org.txor.bestpokemons.domain.RefreshDataService;
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
    public HeavyService heavyService(RefreshDataService refreshDataService, PokemonDaoToPokemonConverter pokemonDaoToPokemonConverter) {
        return new HeavyService(refreshDataService, pokemonRepository, pokemonDaoToPokemonConverter);
    }

    @Bean
    public HeightService heightService(RefreshDataService refreshDataService, PokemonDaoToPokemonConverter pokemonDaoToPokemonConverter) {
        return new HeightService(refreshDataService, pokemonRepository, pokemonDaoToPokemonConverter);
    }

    @Bean
    public ExperienceService experienceService(RefreshDataService refreshDataService, PokemonDaoToPokemonConverter pokemonDaoToPokemonConverter) {
        return new ExperienceService(refreshDataService, pokemonRepository, pokemonDaoToPokemonConverter);
    }

    @Bean
    public RefreshDataService refreshDataService(PokemonApiClient pokemonApiClient, PokemonFilter pokemonFilter, PokemonApiDTOToPokemonDAOConverter pokemonApiDtoToPokemonDaoConverter) {
        return new RefreshDataService(pokemonRepository, pokemonApiClient, pokemonFilter, pokemonApiDtoToPokemonDaoConverter);
    }
}
