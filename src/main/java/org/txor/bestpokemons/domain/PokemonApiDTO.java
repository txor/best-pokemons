package org.txor.bestpokemons.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PokemonApiDTO {
    private String name;
    private String url;
    private String weight;
    private String height;
    @JsonProperty("base_experience")
    private String experience;
}
