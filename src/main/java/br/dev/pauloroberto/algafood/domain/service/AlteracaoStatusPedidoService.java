package br.dev.pauloroberto.algafood.domain.service;

import br.dev.pauloroberto.algafood.domain.model.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlteracaoStatusPedidoService {
    @Autowired
    private CadastroPedidoService cadastroPedidoService;

    @Transactional
    public void confirmar(Long pedidoId) {
        Pedido pedido = cadastroPedidoService.verificarSeExiste(pedidoId);
        pedido.confirmar();
    }

    @Transactional
    public void entregar(Long pedidoId) {
        Pedido pedido = cadastroPedidoService.verificarSeExiste(pedidoId);
        pedido.entregar();
    }

    @Transactional
    public void cancelar(Long pedidoId) {
        Pedido pedido = cadastroPedidoService.verificarSeExiste(pedidoId);
        pedido.cancelar();
    }

}
