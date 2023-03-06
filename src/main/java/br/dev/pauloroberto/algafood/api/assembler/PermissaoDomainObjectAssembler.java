package br.dev.pauloroberto.algafood.api.assembler;

import br.dev.pauloroberto.algafood.api.model.input.PermissaoInputDto;
import br.dev.pauloroberto.algafood.domain.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PermissaoDomainObjectAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public Permissao toDomainObject(PermissaoInputDto permissaoInput) {
        return modelMapper.map(permissaoInput, Permissao.class);
    }

    public void copyToDomainObject(PermissaoInputDto permissaoInput, Permissao permissao) {
        modelMapper.map(permissaoInput, permissao);
    }

}
