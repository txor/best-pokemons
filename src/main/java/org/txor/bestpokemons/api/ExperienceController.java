package org.txor.bestpokemons.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.txor.bestpokemons.domain.PokemonDTO;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ExperienceController {

    @RequestMapping(value = "/most_experienced", produces = {"application/JSON"})
    public List<PokemonDTO> locations() {
        return new ArrayList<>();
    }
}
