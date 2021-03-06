package org.txor.bestpokemons.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "pokemon")
public class PokemonDAO {

    @Id
    private String name;
    private String url;
    private int weight;
    private int height;
    private int experience;
}
