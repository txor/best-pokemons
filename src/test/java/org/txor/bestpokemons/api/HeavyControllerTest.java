package org.txor.bestpokemons.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.txor.bestpokemons.domain.HeavyService;
import org.txor.bestpokemons.domain.Pokemon;
import org.txor.bestpokemons.domain.PokemonToPokemonDtoConverter;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class HeavyControllerTest {

    @Mock
    private PokemonToPokemonDtoConverter pokemonToPokemonDtoConverter;

    @Mock
    private HeavyService heavyService;

    @Test
    public void heaviest_shouldRelyOnServiceAndConverterToProvidePokemonList() {
        given(heavyService.getTopHeavy5Pokemons()).willReturn(Arrays.asList(new Pokemon(), new Pokemon(), new Pokemon()));
        HeavyController heavyController = new HeavyController(heavyService, pokemonToPokemonDtoConverter);

        heavyController.heaviest();

        verify(heavyService).getTopHeavy5Pokemons();
        verify(pokemonToPokemonDtoConverter, times(3)).convert(any());
    }
}