package org.txor.bestpokemons;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.txor.bestpokemons.api.PokemonDTO;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BestPokemonsAppTest {

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void bestPokemonsApp_shouldReturnThe5HeaviestPokemonsFromPokeapi() {
        PokemonDTO[] pokemons = restTemplate.getForObject("http://localhost:" + port + "/heavy", PokemonDTO[].class);

        assertEquals(5, pokemons.length);
    }
}