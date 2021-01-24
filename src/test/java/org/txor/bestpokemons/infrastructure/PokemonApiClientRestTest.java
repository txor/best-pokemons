package org.txor.bestpokemons.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.client.MockRestServiceServer;
import org.txor.bestpokemons.domain.PokemonApiDTO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(PokemonApiClientRest.class)
@TestPropertySource(properties = "pokemon.api.url=http://localhost:8080/api/v2/pokemon")
public class PokemonApiClientRestTest {

    @Autowired
    private PokemonApiClientRest client;

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() throws Exception {
        String userAgent = "java";
        this.server.expect(requestTo("http://localhost:8080/api/v2/pokemon"))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header("User-Agent", userAgent))
                .andRespond(withSuccess(getBodyFromFile("pokemons_page1.json"), MediaType.APPLICATION_JSON));
        this.server.expect(requestTo("http://localhost:8080/api/v2/pokemon/21/"))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header("User-Agent", userAgent))
                .andRespond(withSuccess(getBodyFromFile("spearow.json"), MediaType.APPLICATION_JSON));
        this.server.expect(requestTo("http://localhost:8080/api/v2/pokemon/22/"))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header("User-Agent", userAgent))
                .andRespond(withSuccess(getBodyFromFile("fearow.json"), MediaType.APPLICATION_JSON));
        this.server.expect(requestTo("http://localhost:8080/api/v2/pokemon/23/"))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header("User-Agent", userAgent))
                .andRespond(withSuccess(getBodyFromFile("ekans.json"), MediaType.APPLICATION_JSON));
        this.server.expect(requestTo("http://localhost:8080/api/v2/pokemon/24/"))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header("User-Agent", userAgent))
                .andRespond(withSuccess(getBodyFromFile("arbok.json"), MediaType.APPLICATION_JSON));
        this.server.expect(requestTo("http://localhost:8080/api/v2/pokemon/25/"))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header("User-Agent", userAgent))
                .andRespond(withSuccess(getBodyFromFile("pikachu.json"), MediaType.APPLICATION_JSON));
        this.server.expect(requestTo("http://localhost:8080/api/v2/pokemon?offset=6&limit=20"))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header("User-Agent", userAgent))
                .andRespond(withSuccess(getBodyFromFile("pokemons_page2.json"), MediaType.APPLICATION_JSON));
        this.server.expect(requestTo("http://localhost:8080/api/v2/pokemon/26/"))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header("User-Agent", userAgent))
                .andRespond(withSuccess(getBodyFromFile("raichu.json"), MediaType.APPLICATION_JSON));
    }

    @Test
    public void getAllPokemons_shouldCallPokeApiForEveryPageAndRetrieveEveryPokemonOnTheResponses() {
        List<PokemonApiDTO> pokemons = this.client.getAllPokemons();

        assertEquals(6, pokemons.size());
    }

    private static String getBodyFromFile(String fileName) throws IOException {
        File file = new File(PokemonApiClientRestTest.class.getResource("/" + fileName).getFile());
        return new String(Files.readAllBytes(file.toPath()));
    }
}
