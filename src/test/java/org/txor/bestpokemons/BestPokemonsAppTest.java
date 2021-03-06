package org.txor.bestpokemons;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.txor.bestpokemons.domain.PokemonDTO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(properties = {"pokemon.api.url=http://localhost:8080/api/v2/pokemon", "pokemon.api.threads=1"}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BestPokemonsAppTest {

    private static final WireMockServer wireMockServer = new WireMockServer(8080);

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @AfterEach
    public void shutdown() {
        wireMockServer.stop();
    }

    @Test
    public void bestPokemonsApp_shouldReturnThe5HeaviestPokemonsFromPokeapi() throws IOException {
        setupHappyPath();

        PokemonDTO[] pokemons = restTemplate.getForObject("http://localhost:" + port + "/heaviest", PokemonDTO[].class);

        assertEquals(5, pokemons.length);
        assertEquals("arbok", pokemons[0].getName());
        assertEquals("fearow", pokemons[1].getName());
        assertEquals("raichu", pokemons[2].getName());
        assertEquals("ekans", pokemons[3].getName());
        assertEquals("pikachu", pokemons[4].getName());
    }

    @Test
    public void bestPokemonsApp_shouldReturnThe5HighestPokemonsFromPokeapi() throws IOException {
        setupHappyPath();

        PokemonDTO[] pokemons = restTemplate.getForObject("http://localhost:" + port + "/tallest", PokemonDTO[].class);

        assertEquals(5, pokemons.length);
        assertEquals("arbok", pokemons[0].getName());
        assertEquals("ekans", pokemons[1].getName());
        assertEquals("fearow", pokemons[2].getName());
        assertEquals("raichu", pokemons[3].getName());
        assertEquals("pikachu", pokemons[4].getName());
    }

    @Test
    public void bestPokemonsApp_shouldReturnThe5MostExperiencedPokemonsFromPokeapi() throws IOException {
        setupHappyPath();

        PokemonDTO[] pokemons = restTemplate.getForObject("http://localhost:" + port + "/most_experienced", PokemonDTO[].class);

        assertEquals(5, pokemons.length);
        assertEquals("raichu", pokemons[0].getName());
        assertEquals("arbok", pokemons[1].getName());
        assertEquals("fearow", pokemons[2].getName());
        assertEquals("pikachu", pokemons[3].getName());
        assertEquals("ekans", pokemons[4].getName());
    }

    @Test
    @Sql("/delete_data.sql")
    public void bestPokemonsApp_shouldReturnNothingIfPokeApiIsNotRespondingAndNeverLoadedPokemonData() {
        wireMockServer.start();
        stubFor(get(urlEqualTo("/api/v2/pokemon"))
                .willReturn(aResponse()
                        .withStatus(404)));

        PokemonDTO[] pokemons = restTemplate.getForObject("http://localhost:" + port + "/most_experienced", PokemonDTO[].class);

        assertEquals(0, pokemons.length);
    }

    private void setupHappyPath() throws IOException {
        wireMockServer.start();
        stubFor(get(urlEqualTo("/api/v2/pokemon"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(getBodyFromFile("pokemons_page1.json"))));
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
        stubFor(get(urlEqualTo("/api/v2/pokemon/304/"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(getBodyFromFile("aron.json"))));
    }

    private String getBodyFromFile(String fileName) throws IOException {
        File file = new File(getClass().getResource("/" + fileName).getFile());
        return new String(Files.readAllBytes(file.toPath()));
    }
}
