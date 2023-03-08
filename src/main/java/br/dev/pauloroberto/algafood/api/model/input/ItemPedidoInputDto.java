package br.dev.pauloroberto.algafood.api.model.input;

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
    private Long produtoId;

    @NotNull
    @PositiveOrZero
    private Integer quantidade;

    private String observacao;
}
