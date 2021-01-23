package org.txor.bestpokemons.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.txor.bestpokemons.domain.HeavyService;
import org.txor.bestpokemons.domain.PokemonToPokemonDtoConverter;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class HeavyController {

    private final HeavyService heavyService;
    private final PokemonToPokemonDtoConverter pokemonToPokemonDtoConverter;

    @Autowired
    public HeavyController(HeavyService heavyService, PokemonToPokemonDtoConverter pokemonToPokemonDtoConverter) {
        this.heavyService = heavyService;
        this.pokemonToPokemonDtoConverter = pokemonToPokemonDtoConverter;
    }

    @RequestMapping(value = "/heaviest", produces = {"application/JSON"})
    public List<PokemonDTO> heaviest() {
        return heavyService.getTopHeavy5Pokemons().stream().map(pokemonToPokemonDtoConverter::convert).collect(Collectors.toList());
    }
}
