package br.dev.pauloroberto.algafood.domain.model.mixin;

import br.dev.pauloroberto.algafood.domain.model.Estado;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public abstract class CidadeMixin {
    @JsonIgnoreProperties(value = "nome", allowGetters = true)
    private Estado estado;
}
