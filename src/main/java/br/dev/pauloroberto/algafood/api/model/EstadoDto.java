package br.dev.pauloroberto.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Setter
@Getter
public class EstadoDto extends RepresentationModel<EstadoDto> {
    @ApiModelProperty(example = "1", required = true)
    private Long id;
    @ApiModelProperty(example = "São Paulo", required = true)
    private String nome;
}
