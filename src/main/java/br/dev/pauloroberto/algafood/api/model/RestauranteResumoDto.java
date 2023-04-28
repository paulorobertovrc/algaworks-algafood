package br.dev.pauloroberto.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
@Relation(collectionRelation = "restaurantes")
public class RestauranteResumoDto extends RepresentationModel<RestauranteResumoDto> {
    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "O Rei do Balde de Frango Frito")
    private String nome;
}
