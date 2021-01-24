package org.txor.bestpokemons.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientResponseException;
import org.txor.bestpokemons.repository.PokemonRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
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
    LogService logService;

    @Mock
    private PokemonApiDTOToPokemonDAOConverter pokemonApiDTOToPokemonDAOConverter;

    final ArgumentCaptor<List> captor = ArgumentCaptor.forClass(List.class);

    @Test
    public void refreshData_shouldLoadOnTheRepositoryEveryPokemonFromExternalApiThatIsAllowedByThePokemonFilter() {
        given(pokemonApiClient.getAllPokemons()).willReturn(Arrays.asList(new PokemonApiDTO(), new PokemonApiDTO()));
        given(pokemonFilter.filter(any())).willReturn(true).willReturn(false);
        RefreshDataService refreshDataService = new RefreshDataService(pokemonRepository, pokemonApiClient, pokemonFilter, pokemonApiDTOToPokemonDAOConverter, logService);

        refreshDataService.refreshData();

        verify(pokemonApiClient).getAllPokemons();
        verify(pokemonFilter, times(2)).filter(any());
        verify(pokemonApiDTOToPokemonDAOConverter).convert(any());
        verify(pokemonRepository).saveAll(captor.capture());
        verify(logService, never()).log(any());
        assertEquals(1, captor.getValue().size());
    }

    @Test
    public void refreshData_shouldDoNothingAndLogAnErrorOnClientException() {
        given(pokemonApiClient.getAllPokemons()).willThrow(new RestClientResponseException("test error", 404, "some status", null, null, null));
        RefreshDataService refreshDataService = new RefreshDataService(pokemonRepository, pokemonApiClient, pokemonFilter, pokemonApiDTOToPokemonDAOConverter, logService);

        refreshDataService.refreshData();

        verify(pokemonApiClient).getAllPokemons();
        verify(pokemonFilter, never()).filter(any());
        verify(pokemonApiDTOToPokemonDAOConverter, never()).convert(any());
        verify(pokemonRepository, never()).saveAll(any());
        verify(logService).log(any());
    }
}
