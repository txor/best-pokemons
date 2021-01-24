package org.txor.bestpokemons.infrastructure;

import lombok.Data;

import java.util.List;

@Data
class PokemonPageDTO {
    private String count;
    private String next;
    private List<PokemonReferenceDTO> results;
}
