package br.dev.pauloroberto.algafood.api.v2.assembler;

import br.dev.pauloroberto.algafood.api.v2.controller.CozinhaControllerV2;
import br.dev.pauloroberto.algafood.api.v2.helper.LinkHelperV2;
import br.dev.pauloroberto.algafood.api.v2.model.CozinhaDtoV2;
import br.dev.pauloroberto.algafood.domain.model.Cozinha;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class CozinhaDtoAssemblerV2 extends RepresentationModelAssemblerSupport<Cozinha, CozinhaDtoV2> {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private LinkHelperV2 linkHelper;

    public CozinhaDtoAssemblerV2() {
        super(CozinhaControllerV2.class, CozinhaDtoV2.class);
    }

    @Override
    public @NotNull CozinhaDtoV2 toModel(@NotNull Cozinha cozinha) {
        CozinhaDtoV2 cozinhaDto = createModelWithId(cozinha.getId(), cozinha);
        modelMapper.map(cozinha, cozinhaDto);
        cozinhaDto.add(linkHelper.linkToCozinhas("cozinhas"));

        return cozinhaDto;
    }

    @Override
    public @NotNull CollectionModel<CozinhaDtoV2> toCollectionModel(@NotNull Iterable<? extends Cozinha> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(CozinhaControllerV2.class).withSelfRel());
    }

}
