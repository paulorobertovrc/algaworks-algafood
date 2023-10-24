package br.dev.pauloroberto.algafood.api.v1.assembler;

import br.dev.pauloroberto.algafood.api.v1.controller.CozinhaController;
import br.dev.pauloroberto.algafood.api.v1.helper.LinkHelper;
import br.dev.pauloroberto.algafood.api.v1.model.CozinhaDto;
import br.dev.pauloroberto.algafood.domain.model.Cozinha;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class CozinhaDtoAssembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaDto> {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private LinkHelper linkHelper;

    public CozinhaDtoAssembler() {
        super(CozinhaController.class, CozinhaDto.class);
    }

    @Override
    public @NotNull CozinhaDto toModel(@NotNull Cozinha cozinha) {
        CozinhaDto cozinhaDto = modelMapper.map(cozinha, CozinhaDto.class);

        cozinhaDto.add(linkHelper.linkToCozinha(cozinhaDto.getId()));
        cozinhaDto.add(linkHelper.linkToCozinhas("cozinhas"));

        return cozinhaDto;
    }

    @Override
    public @NotNull CollectionModel<CozinhaDto> toCollectionModel(@NotNull Iterable<? extends Cozinha> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(CozinhaController.class).withSelfRel());
    }
}
