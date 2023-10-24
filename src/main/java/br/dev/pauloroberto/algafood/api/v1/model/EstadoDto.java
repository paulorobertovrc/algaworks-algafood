package br.dev.pauloroberto.algafood.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Setter
@Getter
@Relation(collectionRelation = "estados")
public class EstadoDto extends RepresentationModel<EstadoDto> {
    @ApiModelProperty(example = "1", required = true)
    private Long id;
    @ApiModelProperty(example = "São Paulo", required = true)
    private String nome;
}
