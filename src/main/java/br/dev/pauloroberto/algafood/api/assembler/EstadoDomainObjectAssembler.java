package br.dev.pauloroberto.algafood.api.assembler;

import br.dev.pauloroberto.algafood.api.model.input.EstadoInputDto;
import br.dev.pauloroberto.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EstadoDomainObjectAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public Estado toDomainObject(EstadoInputDto estadoInput) {
        return modelMapper.map(estadoInput, Estado.class);
    }

    public void copyToDomainObject(EstadoInputDto estadoInput, Estado estado) {
        modelMapper.map(estadoInput, estado);
    }
}
