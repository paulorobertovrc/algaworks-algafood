package br.dev.pauloroberto.algafood.api.v2.assembler;

import br.dev.pauloroberto.algafood.api.v2.model.input.CidadeInputDtoV2;
import br.dev.pauloroberto.algafood.domain.model.Cidade;
import br.dev.pauloroberto.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CidadeDomainObjectAssemblerV2 {
    @Autowired
    private ModelMapper modelMapper;

    public Cidade toDomainObject(CidadeInputDtoV2 cidadeInput) {
        return modelMapper.map(cidadeInput, Cidade.class);
    }

    public void copyToDomainObject(CidadeInputDtoV2 cidadeInput, Cidade cidade) {
        cidade.setEstado(new Estado());
        modelMapper.map(cidadeInput, cidade);
    }

}
