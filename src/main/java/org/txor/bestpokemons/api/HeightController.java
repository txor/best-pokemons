package org.txor.bestpokemons.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HeightController {

    @RequestMapping(value = "/highest", produces = {"application/JSON"})
    public List<PokemonDTO> locations() {
        return new ArrayList<>();
    }
}
