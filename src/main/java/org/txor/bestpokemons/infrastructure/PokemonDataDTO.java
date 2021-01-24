package org.txor.bestpokemons.infrastructure;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PokemonDataDTO {
    private String name;
    private String url;
    private String weight;
    private String height;
    @JsonProperty("base_experience")
    private String experience;
    @JsonProperty("game_indices")
    private PokemonIndiceDTO[] indices;
}
