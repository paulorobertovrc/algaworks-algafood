package br.dev.pauloroberto.algafood.api.assembler;

import br.dev.pauloroberto.algafood.api.controller.CozinhaController;
import br.dev.pauloroberto.algafood.api.model.CozinhaDto;
import br.dev.pauloroberto.algafood.domain.model.Cozinha;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CozinhaDtoAssembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaDto> {
    @Autowired
    private ModelMapper modelMapper;

    public CozinhaDtoAssembler() {
        super(CozinhaController.class, CozinhaDto.class);
    }

    @Override
    public @NotNull CozinhaDto toModel(@NotNull Cozinha cozinha) {
        CozinhaDto cozinhaDto = modelMapper.map(cozinha, CozinhaDto.class);

        cozinhaDto.add(linkTo(methodOn(CozinhaController.class).buscar(cozinhaDto.getId())).withSelfRel());
        cozinhaDto.add(linkTo(CozinhaController.class).withRel("cozinhas"));
//        cozinhaDto.add(linkTo(methodOn(CozinhaController.class).listar()).withRel("cozinhas"));

        return cozinhaDto;
    }

    @Override
    public @NotNull CollectionModel<CozinhaDto> toCollectionModel(@NotNull Iterable<? extends Cozinha> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(CozinhaController.class).withSelfRel());
    }
}
