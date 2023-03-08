package br.dev.pauloroberto.algafood.api.model.input;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@ToString
public class PedidoInputDto {
    @Valid
    @NotNull
    private RestauranteIdInputDto restaurante;

    @Valid
    @NotNull
    private FormaPagamentoIdInputDto formaPagamento;

    @Valid
    @NotNull
    private EnderecoInputDto enderecoEntrega;

    @Valid
    @NotNull
    @Size(min = 1)
    private List<ItemPedidoInputDto> itens;
}
