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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class MultithreadedPokemonApiClientRest implements PokemonApiClient {

    private static final String userAgent = "java";

    private final RestTemplate restTemplate;
    private final String startUrl;
    private final Integer poolSize;
    public static final int PAGE_SIZE = 50;

    public MultithreadedPokemonApiClientRest(RestTemplateBuilder restTemplateBuilder, @Value("${pokemon.api.url}") String startUrl, @Value("${pokemon.api.threads}") Integer poolSize) {
        this.restTemplate = restTemplateBuilder.build();
        this.startUrl = startUrl;
        this.poolSize = poolSize;
    }

    @Override
    public List<PokemonApiDTO> getAllPokemons() {
        List<PokemonApiDTO> allPokemons = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);

        List<Future<PokemonApiDTO>> pokemonFutures = new ArrayList<>();

        PokemonPageDTO firstPokemonPage = getForObject(startUrl, PokemonPageDTO.class);
        for (PokemonReferenceDTO pokemonReference : firstPokemonPage.getResults()) {
            pokemonFutures.add(executor.submit(new PokemonDataWorker(pokemonReference)));
        }

        int pokemonCount = Integer.parseInt(firstPokemonPage.getCount());
        if (PAGE_SIZE < pokemonCount) {
            List<Future<PokemonPageDTO>> pokemonPageDTOFutures = new ArrayList<>();
            for (int i = 20; i < pokemonCount; i += PAGE_SIZE) {
                pokemonPageDTOFutures.add(executor.submit(new PokemonPageWorker(startUrl + "?offset=" + i + "&limit=" + PAGE_SIZE)));
            }
            for (Future<PokemonPageDTO> future : pokemonPageDTOFutures) {
                try {
                    PokemonPageDTO pokemonPage = future.get();
                    for (PokemonReferenceDTO pokemonReference : pokemonPage.getResults()) {
                        pokemonFutures.add(executor.submit(new PokemonDataWorker(pokemonReference)));
                    }
                } catch (Exception ignored) {
                }
            }
        }

        for (Future<PokemonApiDTO> future : pokemonFutures) {
            try {
                allPokemons.add(future.get());
            } catch (Exception ignored) {
            }
        }

        return allPokemons;
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

    private class PokemonPageWorker implements Callable<PokemonPageDTO> {

        private final String url;

        public PokemonPageWorker(String url) {
            this.url = url;
        }

        @Override
        public PokemonPageDTO call() {
            return getForObject(url, PokemonPageDTO.class);
        }
    }

    private class PokemonDataWorker implements Callable<PokemonApiDTO> {

        private final PokemonReferenceDTO pokemonReference;

        public PokemonDataWorker(PokemonReferenceDTO pokemonReference) {
            this.pokemonReference = pokemonReference;
        }

        @Override
        public PokemonApiDTO call() {
            return createPokemonApiDTO(pokemonReference, getForObject(pokemonReference.getUrl(), PokemonDataDTO.class));
        }
    }
}
