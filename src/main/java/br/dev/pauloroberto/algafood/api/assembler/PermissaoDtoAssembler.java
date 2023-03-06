package br.dev.pauloroberto.algafood.api.assembler;

import br.dev.pauloroberto.algafood.api.model.PermissaoDto;
import br.dev.pauloroberto.algafood.api.model.input.PermissaoInputDto;
import br.dev.pauloroberto.algafood.domain.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class PermissaoDtoAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public PermissaoDto toDto(Permissao permissao) {
        return modelMapper.map(permissao, PermissaoDto.class);
    }

    public List<PermissaoDto> toDtoList(Collection<Permissao> permissoes) {
        return permissoes.stream()
                .map(this::toDto)
                .toList();
    }

    public PermissaoInputDto toInputDto(Permissao permissao) {
        return modelMapper.map(permissao, PermissaoInputDto.class);
    }

}
