package br.dev.pauloroberto.algafood.api.v2.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Setter
@Getter
@Relation(collectionRelation = "cozinhas")
public class CozinhaDtoV2 extends RepresentationModel<CozinhaDtoV2> {
    @ApiModelProperty(example = "1")
    public Long idCozinha;

    @ApiModelProperty(example = "Brasileira")
    public String nomeCozinha;
}
