package br.dev.pauloroberto.algafood.api.controller;

import br.dev.pauloroberto.algafood.api.assembler.PedidoDomainObjectAssembler;
import br.dev.pauloroberto.algafood.api.assembler.PedidoDtoAssembler;
import br.dev.pauloroberto.algafood.api.assembler.PedidoResumoDtoAssembler;
import br.dev.pauloroberto.algafood.api.model.PedidoDto;
import br.dev.pauloroberto.algafood.api.model.PedidoResumoDto;
import br.dev.pauloroberto.algafood.api.model.input.PedidoInputDto;
import br.dev.pauloroberto.algafood.domain.model.Pedido;
import br.dev.pauloroberto.algafood.domain.model.Usuario;
import br.dev.pauloroberto.algafood.domain.service.CadastroPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    @Autowired
    private CadastroPedidoService cadastroPedidoService;
    @Autowired
    private PedidoDtoAssembler pedidoDtoAssembler;
    @Autowired
    private PedidoResumoDtoAssembler pedidoResumoDtoAssembler;
    @Autowired
    private PedidoDomainObjectAssembler pedidoDomainObjectAssembler;

    @GetMapping
    public List<PedidoResumoDto> listar() {
        return pedidoResumoDtoAssembler.toDtoList(cadastroPedidoService.listar());
    }

    @GetMapping("/{id}")
    public PedidoDto buscar(@PathVariable Long id) {
        Pedido pedido = cadastroPedidoService.verificarSeExiste(id);

        return pedidoDtoAssembler.toDto(pedido);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoDto adicionar(@RequestBody @Valid PedidoInputDto pedidoInput) {
        Pedido pedido = pedidoDomainObjectAssembler.toDomainObject(pedidoInput);

        // TODO: pegar usuário autenticado
        pedido.setCliente(new Usuario());
        pedido.getCliente().setId(1L);

        cadastroPedidoService.emitir(pedido);

        return pedidoDtoAssembler.toDto(pedido);
    }

}
