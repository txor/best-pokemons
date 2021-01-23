package org.txor.bestpokemons.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.txor.bestpokemons.repository.PokemonRepository;

import java.util.Arrays;
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
    private PokemonApiDTOToPokemonDAOConverter pokemonApiDTOToPokemonDAOConverter;

    final ArgumentCaptor<List> captor = ArgumentCaptor.forClass(List.class);

    @Test
    public void refreshData_shouldReloadOnTheRepositoryEveryPokemonFromExternalApi() {
        given(pokemonApiClient.getAllPokemons()).willReturn(Arrays.asList(new PokemonApiDTO(), new PokemonApiDTO()));
        RefreshDataService refreshDataService = new RefreshDataService(pokemonRepository, pokemonApiClient, pokemonApiDTOToPokemonDAOConverter);

        refreshDataService.refreshData();

        verify(pokemonApiClient).getAllPokemons();
        verify(pokemonApiDTOToPokemonDAOConverter, times(2)).convert(any());
        verify(pokemonRepository).saveAll(captor.capture());
        assertEquals(2, captor.getValue().size());
    }
}
