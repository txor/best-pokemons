package org.txor.bestpokemons.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClientResponseException;
import org.txor.bestpokemons.domain.PokemonApiDTO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withBadRequest;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(MultithreadedPokemonApiClientRest.class)
@TestPropertySource(properties = {"pokemon.api.url=http://localhost:8080/api/v2/pokemon", "pokemon.api.threads=1"})
public class MultithreadedPokemonApiClientRestTest {

    private static final String userAgent = "java";

    @Autowired
    private MultithreadedPokemonApiClientRest client;

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllPokemons_shouldCallPokeApiForEveryPageAndRetrieveEveryPokemonOnTheResponses() throws IOException {
        this.server.expect(requestTo("http://localhost:8080/api/v2/pokemon"))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header("User-Agent", userAgent))
                .andRespond(withSuccess(getBodyFromFile("pokemons_page1_simple.json"), MediaType.APPLICATION_JSON));
        this.server.expect(requestTo("http://localhost:8080/api/v2/pokemon/21/"))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header("User-Agent", userAgent))
                .andRespond(withSuccess(getBodyFromFile("spearow.json"), MediaType.APPLICATION_JSON));

        List<PokemonApiDTO> pokemons = this.client.getAllPokemons();

        assertEquals(1, pokemons.size());
    }


    @Test
    public void getAllPokemons_shouldThrowExceptionOnPokeApiError() {
        this.server.expect(
                requestTo("http://localhost:8080/api/v2/pokemon"))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header("User-Agent", userAgent))
                .andRespond(withBadRequest());

        assertThrows(RestClientResponseException.class, () -> this.client.getAllPokemons());
    }

    private String getBodyFromFile(String fileName) throws IOException {
        File file = new File(getClass().getResource("/" + fileName).getFile());
        return new String(Files.readAllBytes(file.toPath()));
    }
}
