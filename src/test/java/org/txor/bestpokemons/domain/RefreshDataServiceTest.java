package org.txor.bestpokemons.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.txor.bestpokemons.repository.PokemonRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class RefreshDataServiceTest {

    @Mock
    private PokemonRepository pokemonRepository;

    @Mock
    private PokemonApiClient pokemonApiClient;

    @Mock
    private PokemonFilter pokemonFilter;

    @Mock
    private PokemonApiDTOToPokemonDAOConverter pokemonApiDTOToPokemonDAOConverter;

    final ArgumentCaptor<List> captor = ArgumentCaptor.forClass(List.class);


    @Test
    public void refreshData_shouldLoadOnTheRepositoryEveryPokemonFromExternalApiThatIsAllowedByThePokemonFilter() {
        given(pokemonApiClient.getAllPokemons()).willReturn(Arrays.asList(new PokemonApiDTO(), new PokemonApiDTO()));
        given(pokemonFilter.filter(any())).willReturn(true).willReturn(false);
        RefreshDataService refreshDataService = new RefreshDataService(pokemonRepository, pokemonApiClient, pokemonFilter, pokemonApiDTOToPokemonDAOConverter);

        refreshDataService.refreshData();

        verify(pokemonApiClient).getAllPokemons();
        verify(pokemonFilter, times(2)).filter(any());
        verify(pokemonApiDTOToPokemonDAOConverter).convert(any());
        verify(pokemonRepository).saveAll(captor.capture());
        assertEquals(1, captor.getValue().size());
    }
}
