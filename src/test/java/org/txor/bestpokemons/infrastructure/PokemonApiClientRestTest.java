package org.txor.bestpokemons.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.client.MockRestServiceServer;
import org.txor.bestpokemons.domain.PokemonApiDTO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        this.server.expect(requestTo("http://localhost:8080/api/v2/pokemon"))
                .andRespond(withSuccess(getBodyFromFile("pokemons_page1.json"), MediaType.APPLICATION_JSON));
        this.server.expect(requestTo("http://localhost:8080/api/v2/pokemon/21/"))
                .andRespond(withSuccess(getBodyFromFile("spearow.json"), MediaType.APPLICATION_JSON));
        this.server.expect(requestTo("http://localhost:8080/api/v2/pokemon/22/"))
                .andRespond(withSuccess(getBodyFromFile("fearow.json"), MediaType.APPLICATION_JSON));
        this.server.expect(requestTo("http://localhost:8080/api/v2/pokemon/23/"))
                .andRespond(withSuccess(getBodyFromFile("ekans.json"), MediaType.APPLICATION_JSON));
        this.server.expect(requestTo("http://localhost:8080/api/v2/pokemon/24/"))
                .andRespond(withSuccess(getBodyFromFile("arbok.json"), MediaType.APPLICATION_JSON));
        this.server.expect(requestTo("http://localhost:8080/api/v2/pokemon/25/"))
                .andRespond(withSuccess(getBodyFromFile("pikachu.json"), MediaType.APPLICATION_JSON));
        this.server.expect(requestTo("http://localhost:8080/api/v2/pokemon?offset=6&limit=20"))
                .andRespond(withSuccess(getBodyFromFile("pokemons_page2.json"), MediaType.APPLICATION_JSON));
        this.server.expect(requestTo("http://localhost:8080/api/v2/pokemon/26/"))
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
