package br.dev.pauloroberto.algafood.domain.model.mixin;

import br.dev.pauloroberto.algafood.domain.model.Restaurante;
import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class ProdutoMixin {
    @JsonIgnore
    private Restaurante restaurante;
}
