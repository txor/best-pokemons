package org.txor.bestpokemons.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.txor.bestpokemons.domain.HeightService;
import org.txor.bestpokemons.domain.Pokemon;
import org.txor.bestpokemons.domain.PokemonDTO;
import org.txor.bestpokemons.domain.PokemonToPokemonDtoConverter;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class HeightControllerTest {

    @Mock
    private PokemonToPokemonDtoConverter pokemonToPokemonDtoConverter;

    @Mock
    private HeightService heightService;

    @Test
    public void highest_shouldRelyOnServiceAndConverterToProvidePokemonList() {
        given(heightService.getTopTallest5Pokemons()).willReturn(Arrays.asList(new Pokemon(), new Pokemon(), new Pokemon()));
        HeightController heavyController = new HeightController(heightService, pokemonToPokemonDtoConverter);

        List<PokemonDTO> pokemons = heavyController.tallest();

        assertEquals(3, pokemons.size());
        verify(heightService).getTopTallest5Pokemons();
        verify(pokemonToPokemonDtoConverter, times(3)).convert(any());
    }
}
