package br.dev.pauloroberto.algafood.api.assembler;

import br.dev.pauloroberto.algafood.api.model.input.PedidoInputDto;
import br.dev.pauloroberto.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoDomainObjectAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public Pedido toDomainObject(PedidoInputDto pedidoInput) {
        return modelMapper.map(pedidoInput, Pedido.class);
    }

    public void copyToDomainObject(PedidoInputDto pedidoInput, Pedido pedido) {
        modelMapper.map(pedidoInput, pedido);
    }

}
