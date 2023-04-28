package br.dev.pauloroberto.algafood.api.assembler;

import br.dev.pauloroberto.algafood.api.controller.*;
import br.dev.pauloroberto.algafood.api.model.PedidoDto;
import br.dev.pauloroberto.algafood.domain.model.Pedido;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PedidoDtoAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoDto> {
    @Autowired
    private ModelMapper modelMapper;

    public PedidoDtoAssembler() {
        super(PedidoController.class, PedidoDto.class);
    }

    @Override
    public @NotNull PedidoDto toModel(@NotNull Pedido pedido) {
        PedidoDto pedidoDto = modelMapper.map(pedido, PedidoDto.class);

        pedidoDto.add(linkTo(methodOn(PedidoController.class)
                .buscar(pedidoDto.getCodigo())).withSelfRel());
        pedidoDto.add(linkTo(PedidoController.class).withRel("pedidos"));
        pedidoDto.getRestaurante().add(linkTo(methodOn(RestauranteController.class)
                .buscar(pedidoDto.getRestaurante().getId())).withSelfRel());
        pedidoDto.getCliente().add(linkTo(methodOn(UsuarioController.class)
                .buscar(pedidoDto.getCliente().getId())).withSelfRel());
        pedidoDto.getFormaPagamento().add(linkTo(methodOn(FormaPagamentoController.class)
                .buscar(pedidoDto.getFormaPagamento().getId(), null)).withSelfRel());
        pedidoDto.getEnderecoEntrega().getCidade().add(linkTo(methodOn(CidadeController.class)
                .buscar(pedidoDto.getEnderecoEntrega().getCidade().getId())).withSelfRel());

        pedidoDto.getItens().forEach(item -> item.add(linkTo(methodOn(RestauranteProdutoController.class)
                .buscar(pedidoDto.getRestaurante().getId(), item.getProdutoId())).withRel("produto")));

        return pedidoDto;
    }

}
