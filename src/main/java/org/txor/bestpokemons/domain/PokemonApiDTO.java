package org.txor.bestpokemons.domain;

import lombok.Data;

import java.util.List;

@Data
public class PokemonApiDTO {
    private String name;
    private String url;
    private String weight;
    private String height;
    private String experience;
    private List<String> versions;
}
