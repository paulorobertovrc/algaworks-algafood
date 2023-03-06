package br.dev.pauloroberto.algafood.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PermissaoInputDto {
    @NotBlank
    private String nome;
    @NotBlank
    private String descricao;
}
