package org.txor.bestpokemons.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.txor.bestpokemons.domain.HeightService;
import org.txor.bestpokemons.domain.PokemonDTO;
import org.txor.bestpokemons.domain.PokemonToPokemonDtoConverter;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class HeightController {

    private final HeightService heightService;
    private final PokemonToPokemonDtoConverter pokemonToPokemonDtoConverter;

    public HeightController(HeightService heightService, PokemonToPokemonDtoConverter pokemonToPokemonDtoConverter) {
        this.heightService = heightService;
        this.pokemonToPokemonDtoConverter = pokemonToPokemonDtoConverter;
    }

    @RequestMapping(value = "/highest", produces = {"application/JSON"})
    public List<PokemonDTO> highest() {
        return heightService.getTopTallest5Pokemons().stream().map(pokemonToPokemonDtoConverter::convert).collect(Collectors.toList());
    }
}
