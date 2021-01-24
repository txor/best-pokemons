package org.txor.bestpokemons.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.txor.bestpokemons.domain.ExperienceService;
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
class ExperienceControllerTest {

    @Mock
    private PokemonToPokemonDtoConverter pokemonToPokemonDtoConverter;

    @Mock
    private ExperienceService experienceService;

    @Test
    public void highest_shouldRelyOnServiceAndConverterToProvidePokemonList() {
        given(experienceService.getTopExperienced5Pokemons()).willReturn(Arrays.asList(new Pokemon(), new Pokemon(), new Pokemon()));
        ExperienceController experienceController = new ExperienceController(experienceService, pokemonToPokemonDtoConverter);

        List<PokemonDTO> pokemons = experienceController.mostExperienced();

        assertEquals(3, pokemons.size());
        verify(experienceService).getTopExperienced5Pokemons();
        verify(pokemonToPokemonDtoConverter, times(3)).convert(any());
    }
}
