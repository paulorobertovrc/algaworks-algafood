package br.dev.pauloroberto.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Getter
@Setter
@Relation(collectionRelation = "produtos")
public class ProdutoDto extends RepresentationModel<ProdutoDto> {
    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Pizza de Calabresa")
    private String nome;

    @ApiModelProperty(example = "Feita com molho de tomates italianos, mussarela, calabresa e orégano")
    private String descricao;

    @ApiModelProperty(example = "50.00")
    private BigDecimal preco;

    @ApiModelProperty(example = "true")
    private Boolean ativo;
}
