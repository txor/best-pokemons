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
public class HeavyServiceTest {

    @Mock
    private RefreshDataService refreshDataService;

    @Mock
    private PokemonRepository pokemonRepository;

    @Mock
    private PokemonDaoToPokemonConverter pokemonDaoToPokemonConverter;

    @Test
    public void getTopHeavy5Pokemons_shouldCallRefreshDataServiceBeforeCallingReposirory() {
        HeavyService heavyService = new HeavyService(refreshDataService, pokemonRepository, pokemonDaoToPokemonConverter);

        heavyService.getTopHeavy5Pokemons();

        verify(refreshDataService).refreshData();
        verify(pokemonRepository).getHeaviest();
    }

    @Test
    public void getTopHeavy5Pokemons_shouldCallRepositoryToGetThePokemons() {
        given(pokemonRepository.getHeaviest()).willReturn(Arrays.asList(new PokemonDAO(), new PokemonDAO()));
        HeavyService heavyService = new HeavyService(refreshDataService, pokemonRepository, pokemonDaoToPokemonConverter);

        List<Pokemon> pokemons = heavyService.getTopHeavy5Pokemons();

        assertEquals(2, pokemons.size());
        verify(pokemonRepository).getHeaviest();
    }
}
