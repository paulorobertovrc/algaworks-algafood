package br.dev.pauloroberto.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EstadoDto {
    @ApiModelProperty(example = "1", required = true)
    private Long id;
    @ApiModelProperty(example = "São Paulo", required = true)
    private String nome;
}
