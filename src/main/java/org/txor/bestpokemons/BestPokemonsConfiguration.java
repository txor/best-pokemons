package org.txor.bestpokemons;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.txor.bestpokemons.domain.HeavyService;
import org.txor.bestpokemons.domain.PokemonToPokemonDtoConverter;

@Configuration
public class BestPokemonsConfiguration {

    @Bean
    public PokemonToPokemonDtoConverter pokemonToPokemonDtoConverter() {
        return new PokemonToPokemonDtoConverter();
    }

    @Bean
    public HeavyService heavyService() {
        return new HeavyService();
    }
}
