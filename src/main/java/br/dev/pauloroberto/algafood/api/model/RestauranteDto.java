package br.dev.pauloroberto.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Setter
@Getter
@Relation(collectionRelation = "restaurantes")
public class RestauranteDto extends RepresentationModel<RestauranteDto> {
//    @JsonView({ RestauranteView.Resumo.class, RestauranteView.ApenasNome.class })
    @ApiModelProperty(example = "1")
    private Long id;

//    @JsonView({ RestauranteView.Resumo.class, RestauranteView.ApenasNome.class })
    @ApiModelProperty(example = "Thai Gourmet")
    private String nome;

//    @JsonView(RestauranteView.Resumo.class)
    @ApiModelProperty(example = "10.00")
    private BigDecimal taxaFrete;

//    @JsonView(RestauranteView.Resumo.class)
    private CozinhaDto cozinha;

    private Boolean ativo;
    private Boolean aberto;
    private EnderecoDto endereco;
}
