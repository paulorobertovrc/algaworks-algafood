package br.dev.pauloroberto.algafood.api.v1.assembler;

import br.dev.pauloroberto.algafood.api.v1.model.input.CidadeInputDto;
import br.dev.pauloroberto.algafood.domain.model.Cidade;
import br.dev.pauloroberto.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CidadeDomainObjectAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public Cidade toDomainObject(CidadeInputDto cidadeInput) {
        return modelMapper.map(cidadeInput, Cidade.class);
    }

    public void copyToDomainObject(CidadeInputDto cidadeInput, Cidade cidade) {
        cidade.setEstado(new Estado());

        modelMapper.map(cidadeInput, cidade);
    }

}
