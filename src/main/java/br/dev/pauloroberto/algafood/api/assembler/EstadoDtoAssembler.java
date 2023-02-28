package br.dev.pauloroberto.algafood.api.assembler;

import br.dev.pauloroberto.algafood.api.model.EstadoDto;
import br.dev.pauloroberto.algafood.api.model.input.EstadoInputDto;
import br.dev.pauloroberto.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EstadoDtoAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public EstadoDto toDto(Estado estado) {
        return modelMapper.map(estado, EstadoDto.class);
    }

    public List<EstadoDto> toDtoList(List<Estado> estados) {
        return estados.stream()
                .map(this::toDto)
                .toList();
    }

    public EstadoInputDto toInputDto(Estado estado) {
        return modelMapper.map(estado, EstadoInputDto.class);
    }

}
