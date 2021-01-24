package org.txor.bestpokemons.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.txor.bestpokemons.repository.PokemonRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ExperienceServiceTest {

    @Mock
    private RefreshDataService refreshDataService;

    @Mock
    private PokemonRepository pokemonRepository;

    @Mock
    private PokemonDaoToPokemonConverter pokemonDaoToPokemonConverter;

    @Test
    public void getTopHeavy5Pokemons_shouldCallRefreshDataServiceBeforeCallingReposirory() {
        ExperienceService experienceService = new ExperienceService(refreshDataService, pokemonRepository, pokemonDaoToPokemonConverter);

        experienceService.getTopExperienced5Pokemons();

        verify(refreshDataService).refreshData();
        verify(pokemonRepository).getMostExperienced();
    }

    @Test
    public void getTopHeavy5Pokemons_shouldCallRepositoryToGetThePokemons() {
        given(pokemonRepository.getMostExperienced()).willReturn(Arrays.asList(new PokemonDAO(), new PokemonDAO()));
        ExperienceService experienceService = new ExperienceService(refreshDataService, pokemonRepository, pokemonDaoToPokemonConverter);

        List<Pokemon> pokemons = experienceService.getTopExperienced5Pokemons();

        assertEquals(2, pokemons.size());
        verify(pokemonRepository).getMostExperienced();
    }
}
