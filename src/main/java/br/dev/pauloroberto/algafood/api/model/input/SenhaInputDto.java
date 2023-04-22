package br.dev.pauloroberto.algafood.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SenhaInputDto {
    @NotBlank
    @ApiModelProperty(example = "123", required = true)
    private String senhaAtual;

    @NotBlank
    @ApiModelProperty(example = "456", required = true)
    private String novaSenha;
}
