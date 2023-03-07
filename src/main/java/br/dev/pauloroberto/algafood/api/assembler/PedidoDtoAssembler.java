package br.dev.pauloroberto.algafood.api.assembler;

import br.dev.pauloroberto.algafood.api.model.PedidoDto;
import br.dev.pauloroberto.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoDtoAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public PedidoDto toDto(Pedido pedido) {
        return modelMapper.map(pedido, PedidoDto.class);
    }

    public List<PedidoDto> toDtoList(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(this::toDto)
                .toList();
    }

}
