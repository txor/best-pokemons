package org.txor.bestpokemons;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.txor.bestpokemons.api.PokemonDTO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BestPokemonsAppTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule();

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @BeforeAll
    static void setup() throws IOException {
        stubFor(get(urlEqualTo("/api/v2/pokemon"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(getBodyFromFile("pokemons.json"))));
        stubFor(get(urlEqualTo("/api/v2/pokemon/21"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(getBodyFromFile("spearow.json"))));
        stubFor(get(urlEqualTo("/api/v2/pokemon/22"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(getBodyFromFile("fearow.json"))));
        stubFor(get(urlEqualTo("/api/v2/pokemon/23"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(getBodyFromFile("ekans.json"))));
        stubFor(get(urlEqualTo("/api/v2/pokemon/24"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(getBodyFromFile("arbok.json"))));
        stubFor(get(urlEqualTo("/api/v2/pokemon/25"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(getBodyFromFile("pikachu.json"))));
        stubFor(get(urlEqualTo("/api/v2/pokemon/26"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(getBodyFromFile("raichu.json"))));
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