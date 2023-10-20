package br.dev.pauloroberto.algafood.api.assembler;

import br.dev.pauloroberto.algafood.api.controller.GrupoController;
import br.dev.pauloroberto.algafood.api.helper.LinkHelper;
import br.dev.pauloroberto.algafood.api.model.GrupoDto;
import br.dev.pauloroberto.algafood.api.model.input.GrupoInputDto;
import br.dev.pauloroberto.algafood.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GrupoDtoAssembler extends RepresentationModelAssemblerSupport<Grupo, GrupoDto> {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private LinkHelper linkHelper;

    public GrupoDtoAssembler() {
        super(GrupoController.class, GrupoDto.class);
    }

    public GrupoDto toDto(Grupo grupo) {
        return modelMapper.map(grupo, GrupoDto.class);
    }

    public List<GrupoDto> toDtoList(List<Grupo> grupos) {
        return grupos.stream()
                .map(this::toDto)
                .toList();
    }

    public GrupoInputDto toInputDto(Grupo grupo) {
        return modelMapper.map(grupo, GrupoInputDto.class);
    }

    @Override
    public GrupoDto toModel(Grupo grupo) {
        GrupoDto grupoDto = createModelWithId(grupo.getId(), grupo);
        modelMapper.map(grupo, grupoDto);

        grupoDto.add(linkHelper.linkToGrupos("grupos"));
        grupoDto.add(linkHelper.linkToGrupoPermissoes(grupo.getId(), "permissoes"));

        return grupoDto;
    }

}
