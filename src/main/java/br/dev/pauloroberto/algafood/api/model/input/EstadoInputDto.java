package br.dev.pauloroberto.algafood.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class EstadoInputDto {
    @NotBlank
    private String nome;
}
