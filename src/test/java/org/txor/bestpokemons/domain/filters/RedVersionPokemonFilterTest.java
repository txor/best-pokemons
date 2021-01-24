package org.txor.bestpokemons.domain.filters;

import org.junit.jupiter.api.Test;
import org.txor.bestpokemons.domain.PokemonApiDTO;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RedVersionPokemonFilterTest {

    @Test
    public void filter_shouldReturnTrueIfThePokemonIsInRedVersion() {
        PokemonApiDTO pokemon = new PokemonApiDTO();
        pokemon.setVersions(Arrays.asList("red", "blue"));
        RedVersionPokemonFilter redVersionPokemonFilter = new RedVersionPokemonFilter();

        boolean result = redVersionPokemonFilter.filter(pokemon);

        assertTrue(result);
    }
}
