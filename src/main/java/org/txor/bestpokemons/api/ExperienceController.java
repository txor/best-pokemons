package org.txor.bestpokemons.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.txor.bestpokemons.domain.ExperienceService;
import org.txor.bestpokemons.domain.PokemonDTO;
import org.txor.bestpokemons.domain.PokemonToPokemonDtoConverter;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ExperienceController {

    private final ExperienceService experienceService;
    private final PokemonToPokemonDtoConverter pokemonToPokemonDtoConverter;

    public ExperienceController(ExperienceService experienceService, PokemonToPokemonDtoConverter pokemonToPokemonDtoConverter) {
        this.experienceService = experienceService;
        this.pokemonToPokemonDtoConverter = pokemonToPokemonDtoConverter;
    }

    @RequestMapping(value = "/most_experienced", produces = {"application/JSON"})
    public List<PokemonDTO> mostExperienced() {
        return experienceService.getTopExperienced5Pokemons().stream().map(pokemonToPokemonDtoConverter::convert).collect(Collectors.toList());
    }
}
