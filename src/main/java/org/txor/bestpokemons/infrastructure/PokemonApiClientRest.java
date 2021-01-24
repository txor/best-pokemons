package org.txor.bestpokemons.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.txor.bestpokemons.domain.PokemonApiClient;
import org.txor.bestpokemons.domain.PokemonApiDTO;

import java.util.ArrayList;
import java.util.List;

@Service
public class PokemonApiClientRest implements PokemonApiClient {

    private final RestTemplate restTemplate;
    private final String startUrl;

    public PokemonApiClientRest(RestTemplateBuilder restTemplateBuilder, @Value("${pokemon.api.url}") String startUrl) {
        this.restTemplate = restTemplateBuilder.build();
        this.startUrl = startUrl;
    }

    @Override
    public List<PokemonApiDTO> getAllPokemons() {
        ArrayList<PokemonApiDTO> pokemons = new ArrayList<>();
        String nextUrl = startUrl;
        do {
            PokemonPageDTO pokemonPage = restTemplate.getForObject(nextUrl, PokemonPageDTO.class);
            nextUrl = pokemonPage.getNext();
            for (PokemonReferenceDTO pokemon : pokemonPage.getResults()) {
                PokemonApiDTO pokemonApiDTO = restTemplate.getForObject(pokemon.getUrl(), PokemonApiDTO.class);
                pokemonApiDTO.setName(pokemon.getName());
                pokemonApiDTO.setUrl(pokemon.getUrl());
                pokemons.add(pokemonApiDTO);
            }
        } while (nextUrl != null);

        return pokemons;
    }

}
