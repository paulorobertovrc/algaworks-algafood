package br.dev.pauloroberto.algafood.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class CidadeInputDto {
    @NotBlank
    private String nome;
    @Valid
    @NotNull
    private EstadoIdInputDto estado;
}
