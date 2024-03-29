package br.dev.pauloroberto.algafood.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemPedidoDto extends RepresentationModel<ItemPedidoDto> {
    @ApiModelProperty(example = "1")
    private Long produtoId;

    @ApiModelProperty(example = "Pizza Marguerita")
    private String produtoNome;

    @ApiModelProperty(example = "1")
    private Integer quantidade;

    @ApiModelProperty(example = "42.90")
    private BigDecimal precoUnitario;

    @ApiModelProperty(example = "42.90")
    private BigDecimal precoTotal;

    @ApiModelProperty(example = "Favor enviar guardanapo de papel")
    private String observacao;
}
