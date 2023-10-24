package br.dev.pauloroberto.algafood.api.v1.openapi.model;

import br.dev.pauloroberto.algafood.api.v1.model.CozinhaDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@ApiModel("RestauranteBasicoModel")
public class RestauranteBasicoModelOpenApi {
    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Thai Gourmet")
    private String nome;

    @ApiModelProperty(example = "12.00")
    private BigDecimal taxaFrete;

    private CozinhaDto cozinha;
}
