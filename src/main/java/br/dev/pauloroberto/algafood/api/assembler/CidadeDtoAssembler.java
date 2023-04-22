package br.dev.pauloroberto.algafood.api.assembler;

import br.dev.pauloroberto.algafood.api.controller.CidadeController;
import br.dev.pauloroberto.algafood.api.controller.EstadoController;
import br.dev.pauloroberto.algafood.api.model.CidadeDto;
import br.dev.pauloroberto.algafood.domain.model.Cidade;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CidadeDtoAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeDto> {
    @Autowired
    private ModelMapper modelMapper;

    public CidadeDtoAssembler() {
        super(CidadeController.class, CidadeDto.class);
    }

    @Override
    public @NotNull CidadeDto toModel(@NotNull Cidade cidade) {
        CidadeDto cidadeDto = modelMapper.map(cidade, CidadeDto.class);

        cidadeDto.add(linkTo(methodOn(CidadeController.class).buscar(cidadeDto.getId())).withSelfRel());
        cidadeDto.add(linkTo(methodOn(CidadeController.class).listar()).withRel("cidades"));
        cidadeDto.getEstado().add(linkTo(methodOn(EstadoController.class).buscar(cidadeDto.getEstado().getId()))
                .withSelfRel());

        return cidadeDto;
    }

    @Override
    public @NotNull CollectionModel<CidadeDto> toCollectionModel(@NotNull Iterable<? extends Cidade> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(methodOn(CidadeController.class).listar()).withSelfRel());
    }

}
