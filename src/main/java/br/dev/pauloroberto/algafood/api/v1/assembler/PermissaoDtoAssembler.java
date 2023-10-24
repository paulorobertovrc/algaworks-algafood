package br.dev.pauloroberto.algafood.api.v1.assembler;

import br.dev.pauloroberto.algafood.api.v1.helper.LinkHelper;
import br.dev.pauloroberto.algafood.api.v1.model.PermissaoDto;
import br.dev.pauloroberto.algafood.api.v1.model.input.PermissaoInputDto;
import br.dev.pauloroberto.algafood.domain.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class PermissaoDtoAssembler implements RepresentationModelAssembler<Permissao, PermissaoDto> {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private LinkHelper linkHelper;

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

    @Override
    public PermissaoDto toModel(Permissao permissao) {
        return modelMapper.map(permissao, PermissaoDto.class);
    }

    @Override
    public CollectionModel<PermissaoDto> toCollectionModel(Iterable<? extends Permissao> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities)
                .add(linkHelper.linkToPermissoes());
    }

}
