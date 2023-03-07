package br.dev.pauloroberto.algafood.api.controller;

import br.dev.pauloroberto.algafood.api.assembler.PedidoDtoAssembler;
import br.dev.pauloroberto.algafood.api.model.PedidoDto;
import br.dev.pauloroberto.algafood.domain.model.Pedido;
import br.dev.pauloroberto.algafood.domain.service.CadastroPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    @Autowired
    private CadastroPedidoService cadastroPedidoService;
    @Autowired
    private PedidoDtoAssembler pedidoDtoAssembler;

    @GetMapping
    public List<PedidoDto> listar() {
        return pedidoDtoAssembler.toDtoList(cadastroPedidoService.listar());
    }

    @GetMapping("/{id}")
    public PedidoDto buscar(@PathVariable Long id) {
        Pedido pedido = cadastroPedidoService.verificarSeExiste(id);

        return pedidoDtoAssembler.toDto(pedido);
    }

}
