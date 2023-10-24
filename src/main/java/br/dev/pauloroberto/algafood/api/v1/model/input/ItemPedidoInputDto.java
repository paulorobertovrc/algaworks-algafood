package br.dev.pauloroberto.algafood.api.v1.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@ToString
public class ItemPedidoInputDto {
    @NotNull
    @ApiModelProperty(example = "1", required = true)
    private Long produtoId;

    @NotNull
    @PositiveOrZero
    @ApiModelProperty(example = "1", required = true)
    private Integer quantidade;

    @ApiModelProperty(example = "Sem cebola")
    private String observacao;
}
