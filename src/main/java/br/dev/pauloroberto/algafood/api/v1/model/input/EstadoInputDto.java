package br.dev.pauloroberto.algafood.api.v1.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class EstadoInputDto {
    @NotBlank
    @ApiModelProperty(example = "São Paulo", required = true)
    private String nome;
}
