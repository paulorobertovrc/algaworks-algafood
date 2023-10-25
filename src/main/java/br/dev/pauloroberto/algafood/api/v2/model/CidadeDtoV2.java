package br.dev.pauloroberto.algafood.api.v2.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Setter
@Getter
@Relation(collectionRelation = "cidades")
public class CidadeDtoV2 extends RepresentationModel<CidadeDtoV2> {

    @ApiModelProperty(value = "ID da cidade", example = "1")
    private Long idCidade;

    @ApiModelProperty(value = "Nome da cidade", example = "Campo Grande")
    private String nomeCidade;

    @ApiModelProperty(value = "ID do estado", example = "1")
    private Long idEstado;

    @ApiModelProperty(value = "Nome do estado", example = "Mato Grosso do Sul")
    private String nomeEstado;

}
