package br.dev.pauloroberto.algafood.api.assembler;

import br.dev.pauloroberto.algafood.api.model.PedidoResumoDto;
import br.dev.pauloroberto.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoResumoDtoAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public PedidoResumoDto toDto(Pedido pedido) {
        return modelMapper.map(pedido, PedidoResumoDto.class);
    }

    public List<PedidoResumoDto> toDtoList(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(this::toDto)
                .toList();
    }

}
