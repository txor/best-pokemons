package org.txor.bestpokemons.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "pokemon")
public class PokemonDAO {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String url;
    private String weight;
    private String height;
    private String experience;
}
