package org.txor.bestpokemons.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pokemon")
public class PokemonDAO {

    @Id
    private int id;

}
