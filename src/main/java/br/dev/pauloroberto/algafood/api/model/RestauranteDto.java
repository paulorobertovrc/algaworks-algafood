package br.dev.pauloroberto.algafood.api.model;

import br.dev.pauloroberto.algafood.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class RestauranteDto {
    @JsonView({ RestauranteView.Resumo.class, RestauranteView.ApenasNome.class })
    @ApiModelProperty(example = "1")
    private Long id;

    @JsonView({ RestauranteView.Resumo.class, RestauranteView.ApenasNome.class })
    @ApiModelProperty(example = "Thai Gourmet")
    private String nome;

    @JsonView(RestauranteView.Resumo.class)
    @ApiModelProperty(example = "10.00")
    private BigDecimal taxaFrete;

    @JsonView(RestauranteView.Resumo.class)
    private CozinhaDto cozinha;

    private Boolean ativo;
    private Boolean aberto;
    private EnderecoDto endereco;
}
