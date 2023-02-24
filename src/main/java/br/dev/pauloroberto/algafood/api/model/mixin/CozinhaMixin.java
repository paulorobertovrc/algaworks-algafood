package br.dev.pauloroberto.algafood.api.model.mixin;

import br.dev.pauloroberto.algafood.domain.model.Restaurante;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public abstract class CozinhaMixin {
    @JsonIgnore
    private List<Restaurante> restaurantes;
}
