package br.dev.pauloroberto.algafood.api.v1.assembler;

import br.dev.pauloroberto.algafood.api.v1.controller.EstadoController;
import br.dev.pauloroberto.algafood.api.v1.helper.LinkHelper;
import br.dev.pauloroberto.algafood.api.v1.model.EstadoDto;
import br.dev.pauloroberto.algafood.domain.model.Estado;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class EstadoDtoAssembler extends RepresentationModelAssemblerSupport<Estado, EstadoDto> {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private LinkHelper linkHelper;

    public EstadoDtoAssembler() {
        super(EstadoController.class, EstadoDto.class);
    }

    @Override
    public @NotNull EstadoDto toModel(@NotNull Estado estado) {
        EstadoDto estadoDto = modelMapper.map(estado, EstadoDto.class);

        estadoDto.add(linkHelper.linkToEstado(estadoDto.getId()));
        estadoDto.add(linkHelper.linkToEstados());

        return estadoDto;
    }

    @Override
    public @NotNull CollectionModel<EstadoDto> toCollectionModel(@NotNull Iterable<? extends Estado> entities) {
        return super.toCollectionModel(entities)
                .add(linkHelper.linkToEstados());
    }

}
