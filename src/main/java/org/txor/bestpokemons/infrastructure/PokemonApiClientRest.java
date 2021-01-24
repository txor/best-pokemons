package org.txor.bestpokemons.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.txor.bestpokemons.domain.PokemonApiClient;
import org.txor.bestpokemons.domain.PokemonApiDTO;

import java.util.ArrayList;
import java.util.List;

@Service
public class PokemonApiClientRest implements PokemonApiClient {

    private static final String userAgent = "java";

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
            PokemonPageDTO pokemonPage = getForObject(nextUrl, PokemonPageDTO.class);
            nextUrl = pokemonPage.getNext();
            for (PokemonReferenceDTO pokemonReference : pokemonPage.getResults()) {
                PokemonDataDTO pokemonData = getForObject(pokemonReference.getUrl(), PokemonDataDTO.class);
                PokemonApiDTO pokemonApiDTO = createPokemonApiDTO(pokemonReference, pokemonData);
                pokemons.add(pokemonApiDTO);
            }
        } while (nextUrl != null);

        return pokemons;
    }

    private PokemonApiDTO createPokemonApiDTO(PokemonReferenceDTO pokemonReference, PokemonDataDTO pokemonData) {
        PokemonApiDTO pokemonApiDTO = new PokemonApiDTO();

        pokemonApiDTO.setName(pokemonReference.getName());
        pokemonApiDTO.setUrl(pokemonReference.getUrl());
        pokemonApiDTO.setWeight(pokemonData.getWeight());
        pokemonApiDTO.setHeight(pokemonData.getHeight());
        pokemonApiDTO.setExperience(pokemonData.getExperience());
        List<String> versions = new ArrayList<>();
        for (PokemonIndiceDTO index : pokemonData.getIndices()) {
            versions.add(index.getVersion().getName());
        }
        pokemonApiDTO.setVersions(versions);

        return pokemonApiDTO;
    }

    private <T> T getForObject(String url, Class<T> dtoClass) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", userAgent);
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, entity, dtoClass);
        return response.getBody();
    }
}
