package br.dev.pauloroberto.algafood.api.v1.assembler;

import br.dev.pauloroberto.algafood.api.v1.controller.PedidoController;
import br.dev.pauloroberto.algafood.api.v1.helper.LinkHelper;
import br.dev.pauloroberto.algafood.api.v1.model.PedidoResumoDto;
import br.dev.pauloroberto.algafood.domain.model.Pedido;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PedidoResumoDtoAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoDto> {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private LinkHelper linkHelper;

    public PedidoResumoDtoAssembler() {
        super(PedidoController.class, PedidoResumoDto.class);
    }

    @Override
    public @NotNull PedidoResumoDto toModel(@NotNull Pedido pedido) {
        PedidoResumoDto pedidoResumoDto = modelMapper.map(pedido, PedidoResumoDto.class);

        pedidoResumoDto.add(linkHelper.linkToPedido(pedidoResumoDto.getCodigo()));
        pedidoResumoDto.add(linkHelper.linkToPedidos("pedidos"));
        pedidoResumoDto.add(linkHelper.linkToRestaurante(pedido.getRestaurante().getId()));
        pedidoResumoDto.getCliente().add(linkHelper.linkToUsuario(pedido.getCliente().getId()));

        return pedidoResumoDto;
    }

}
