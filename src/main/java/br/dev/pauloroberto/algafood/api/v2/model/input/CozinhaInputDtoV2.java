package br.dev.pauloroberto.algafood.api.v2.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class CozinhaInputDtoV2 {
    @NotBlank
    @ApiModelProperty(example = "Brasileira", required = true)
    private String nomeCozinha;
}
