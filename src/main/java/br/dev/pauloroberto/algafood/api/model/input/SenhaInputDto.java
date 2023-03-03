package br.dev.pauloroberto.algafood.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SenhaInputDto {
    @NotBlank
    private String senhaAtual;
    @NotBlank
    private String novaSenha;
}
