package br.dev.pauloroberto.algafood.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FormaPagamentoIdInputDto {
    @NotNull
    private Long id;
}
