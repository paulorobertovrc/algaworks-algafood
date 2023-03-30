package br.dev.pauloroberto.algafood.domain.event;

import br.dev.pauloroberto.algafood.domain.model.Pedido;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PedidoConfirmadoEvent {
    private Pedido pedido;
}
