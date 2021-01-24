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
class HeightServiceTest {

    @Mock
    private RefreshDataService refreshDataService;

    @Mock
    private PokemonRepository pokemonRepository;

    @Mock
    private PokemonDaoToPokemonConverter pokemonDaoToPokemonConverter;

    @Test
    public void getTopHeavy5Pokemons_shouldCallRefreshDataServiceBeforeCallingReposirory() {
        HeightService heightService = new HeightService(refreshDataService, pokemonRepository, pokemonDaoToPokemonConverter);

        heightService.getTopTallest5Pokemons();

        verify(refreshDataService).refreshData();
        verify(pokemonRepository).getTallest();
    }

    @Test
    public void getTopHeavy5Pokemons_shouldCallRepositoryToGetThePokemons() {
        given(pokemonRepository.getTallest()).willReturn(Arrays.asList(new PokemonDAO(), new PokemonDAO()));
        HeightService heightService = new HeightService(refreshDataService, pokemonRepository, pokemonDaoToPokemonConverter);

        List<Pokemon> pokemons = heightService.getTopTallest5Pokemons();

        assertEquals(2, pokemons.size());
        verify(pokemonRepository).getTallest();
    }
}
