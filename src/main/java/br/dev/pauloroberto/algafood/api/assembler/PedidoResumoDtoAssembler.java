package br.dev.pauloroberto.algafood.api.assembler;

import br.dev.pauloroberto.algafood.api.controller.PedidoController;
import br.dev.pauloroberto.algafood.api.controller.RestauranteController;
import br.dev.pauloroberto.algafood.api.controller.UsuarioController;
import br.dev.pauloroberto.algafood.api.model.PedidoResumoDto;
import br.dev.pauloroberto.algafood.domain.model.Pedido;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PedidoResumoDtoAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoDto> {
    @Autowired
    private ModelMapper modelMapper;

    public PedidoResumoDtoAssembler() {
        super(PedidoController.class, PedidoResumoDto.class);
    }

    @Override
    public @NotNull PedidoResumoDto toModel(@NotNull Pedido pedido) {
        PedidoResumoDto pedidoResumoDto = modelMapper.map(pedido, PedidoResumoDto.class);

        pedidoResumoDto.add(linkTo(methodOn(PedidoController.class)
                .buscar(pedidoResumoDto.getCodigo())).withSelfRel());
        pedidoResumoDto.add(linkTo(PedidoController.class).withRel("pedidos"));
        pedidoResumoDto.add(linkTo(methodOn(RestauranteController.class)
                .buscar(pedido.getRestaurante().getId())).withSelfRel());
        pedidoResumoDto.getCliente().add(linkTo(methodOn(UsuarioController.class)
                .buscar(pedido.getCliente().getId())).withSelfRel());

        return pedidoResumoDto;
    }

}
