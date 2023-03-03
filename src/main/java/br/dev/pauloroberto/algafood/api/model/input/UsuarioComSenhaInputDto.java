package br.dev.pauloroberto.algafood.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UsuarioComSenhaInputDto extends UsuarioInputDto {
    @NotBlank
    private String senha;
}
