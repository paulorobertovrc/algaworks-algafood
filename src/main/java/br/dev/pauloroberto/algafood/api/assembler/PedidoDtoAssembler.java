package br.dev.pauloroberto.algafood.api.assembler;

import br.dev.pauloroberto.algafood.api.controller.PedidoController;
import br.dev.pauloroberto.algafood.api.helper.LinkHelper;
import br.dev.pauloroberto.algafood.api.model.PedidoDto;
import br.dev.pauloroberto.algafood.domain.model.Pedido;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PedidoDtoAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoDto> {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private LinkHelper linkHelper;

    public PedidoDtoAssembler() {
        super(PedidoController.class, PedidoDto.class);
    }

    @Override
    public @NotNull PedidoDto toModel(@NotNull Pedido pedido) {
        PedidoDto pedidoDto = modelMapper.map(pedido, PedidoDto.class);

        pedidoDto.add(linkHelper.linkToPedido(pedidoDto.getCodigo()));
        pedidoDto.add(linkHelper.linkToPedidos());
        pedidoDto.add(linkHelper.linkToRestaurante(pedido.getRestaurante().getId()));
        pedidoDto.getCliente().add(linkHelper.linkToUsuario(pedido.getCliente().getId()));
        pedidoDto.getFormaPagamento().add(linkHelper.linkToFormasPagamento(pedido.getFormaPagamento().getId()));
        pedidoDto.getEnderecoEntrega().getCidade().add(
                linkHelper.linkToCidade(pedido.getEnderecoEntrega().getCidade().getId()));

        pedidoDto.getItens().forEach(item -> item.add(
                linkHelper.linkToProduto(pedidoDto.getRestaurante().getId(), item.getProdutoId(), "produto")));

        if (pedido.podeSerConfirmado()) {
            pedidoDto.add(linkHelper.linkToConfirmacaoPedido(pedidoDto.getCodigo(), "confirmar"));
        }

        if (pedido.podeSerCancelado()) {
            pedidoDto.add(linkHelper.linkToCancelamentoPedido(pedidoDto.getCodigo(), "cancelar"));
        }

        if (pedido.podeSerEntregue()) {
            pedidoDto.add(linkHelper.linkToEntregaPedido(pedidoDto.getCodigo(), "entregar"));
        }

        return pedidoDto;
    }

}
