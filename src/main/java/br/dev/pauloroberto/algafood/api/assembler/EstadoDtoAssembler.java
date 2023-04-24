package br.dev.pauloroberto.algafood.api.assembler;

import br.dev.pauloroberto.algafood.api.controller.EstadoController;
import br.dev.pauloroberto.algafood.api.model.EstadoDto;
import br.dev.pauloroberto.algafood.domain.model.Estado;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EstadoDtoAssembler extends RepresentationModelAssemblerSupport<Estado, EstadoDto> {
    @Autowired
    private ModelMapper modelMapper;

    public EstadoDtoAssembler() {
        super(EstadoController.class, EstadoDto.class);
    }

    @Override
    public @NotNull EstadoDto toModel(@NotNull Estado estado) {
        EstadoDto estadoDto = modelMapper.map(estado, EstadoDto.class);

        estadoDto.add(linkTo(methodOn(EstadoController.class).buscar(estadoDto.getId())).withSelfRel());
        estadoDto.add(linkTo(methodOn(EstadoController.class).listar()).withRel("estados"));

        return estadoDto;
    }

    @Override
    public @NotNull CollectionModel<EstadoDto> toCollectionModel(@NotNull Iterable<? extends Estado> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(methodOn(EstadoController.class).listar()).withSelfRel());
    }

}
