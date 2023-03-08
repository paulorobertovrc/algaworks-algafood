package br.dev.pauloroberto.algafood.api.controller;

import br.dev.pauloroberto.algafood.domain.service.AlteracaoStatusPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos/{pedidoId}")
public class PedidoAlteracaoStatusController {
    @Autowired
    private AlteracaoStatusPedidoService alteracaoStatusPedidoService;

    @PutMapping("/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmar(@PathVariable Long pedidoId) {
        alteracaoStatusPedidoService.confirmar(pedidoId);
    }

    @PutMapping("/entrega")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void entregar(@PathVariable Long pedidoId) {
        alteracaoStatusPedidoService.entregar(pedidoId);
    }

    @PutMapping("/cancelamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar(@PathVariable Long pedidoId) {
        alteracaoStatusPedidoService.cancelar(pedidoId);
    }

}
