package org.txor.bestpokemons;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.txor.bestpokemons.domain.PokemonDTO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(properties="pokemon.api.url=http://localhost:8080/api/v2/pokemon", webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BestPokemonsAppTest {

    private static final WireMockServer wireMockServer = new WireMockServer(8080);

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @BeforeAll
    static void setup() throws IOException {
        wireMockServer.start();
        stubFor(get(urlEqualTo("/api/v2/pokemon"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(getBodyFromFile("pokemons_page1.json"))));
        stubFor(get(urlEqualTo("/api/v2/pokemon?offset=6&limit=20"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(getBodyFromFile("pokemons_page2.json"))));
        stubFor(get(urlEqualTo("/api/v2/pokemon/21/"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(getBodyFromFile("spearow.json"))));
        stubFor(get(urlEqualTo("/api/v2/pokemon/22/"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(getBodyFromFile("fearow.json"))));
        stubFor(get(urlEqualTo("/api/v2/pokemon/23/"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(getBodyFromFile("ekans.json"))));
        stubFor(get(urlEqualTo("/api/v2/pokemon/24/"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(getBodyFromFile("arbok.json"))));
        stubFor(get(urlEqualTo("/api/v2/pokemon/25/"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(getBodyFromFile("pikachu.json"))));
        stubFor(get(urlEqualTo("/api/v2/pokemon/26/"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(getBodyFromFile("raichu.json"))));
    }

    @AfterAll
    static void shutdown() {
        wireMockServer.stop();
    }

    @Test
    public void bestPokemonsApp_shouldReturnThe5HeaviestPokemonsFromPokeapi() {
        PokemonDTO[] pokemons = restTemplate.getForObject("http://localhost:" + port + "/heaviest", PokemonDTO[].class);

        assertEquals(5, pokemons.length);
        assertEquals("arbok", pokemons[0].getName());
        assertEquals("fearow", pokemons[0].getName());
        assertEquals("raichu", pokemons[0].getName());
        assertEquals("ekans", pokemons[0].getName());
        assertEquals("pickachu", pokemons[0].getName());
    }

    @Test
    public void bestPokemonsApp_shouldReturnThe5HighestPokemonsFromPokeapi() {
        PokemonDTO[] pokemons = restTemplate.getForObject("http://localhost:" + port + "/highest", PokemonDTO[].class);

        assertEquals(5, pokemons.length);
        assertEquals("arbok", pokemons[0].getName());
        assertEquals("ekans", pokemons[0].getName());
        assertEquals("fearow", pokemons[0].getName());
        assertEquals("riachu", pokemons[0].getName());
        assertEquals("pickachu", pokemons[0].getName());
    }

    @Test
    public void bestPokemonsApp_shouldReturnThe5MostExperiencedPokemonsFromPokeapi() {
        PokemonDTO[] pokemons = restTemplate.getForObject("http://localhost:" + port + "/most_experienced", PokemonDTO[].class);

        assertEquals(5, pokemons.length);
        assertEquals("raichu", pokemons[0].getName());
        assertEquals("arbok", pokemons[0].getName());
        assertEquals("fearow", pokemons[0].getName());
        assertEquals("pickachu", pokemons[0].getName());
        assertEquals("ekans", pokemons[0].getName());
    }

    private static String getBodyFromFile(String fileName) throws IOException {
        File file = new File(BestPokemonsAppTest.class.getResource("/" + fileName).getFile());
        return new String(Files.readAllBytes(file.toPath()));
    }
}