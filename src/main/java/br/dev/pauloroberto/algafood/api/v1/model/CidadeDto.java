package br.dev.pauloroberto.algafood.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Setter
@Getter
@Relation(collectionRelation = "cidades") // Define o nome que será exibido no Json de retorno da coleção
//@ApiModel(value = "Cidade", description = "Representa uma cidade") // Define o nome que será exibido na aba "Model" ou "Schemas" do Swagger
public class CidadeDto extends RepresentationModel<CidadeDto> {
    @ApiModelProperty(value = "ID da cidade", example = "1", required = true)
    private Long id;
    @ApiModelProperty(value = "Nome da cidade", example = "São Paulo", required = true)
    private String nome;
    private EstadoDto estado;
}
