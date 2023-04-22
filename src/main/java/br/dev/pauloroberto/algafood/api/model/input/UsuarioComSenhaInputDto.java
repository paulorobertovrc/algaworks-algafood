package br.dev.pauloroberto.algafood.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UsuarioComSenhaInputDto extends UsuarioInputDto {
    @NotBlank
    @ApiModelProperty(example = "123", required = true)
    private String senha;
}
