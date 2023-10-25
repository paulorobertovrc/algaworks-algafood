package br.dev.pauloroberto.algafood.api.v2.assembler;

import br.dev.pauloroberto.algafood.api.v2.controller.CidadeControllerV2;
import br.dev.pauloroberto.algafood.api.v2.helper.LinkHelperV2;
import br.dev.pauloroberto.algafood.api.v2.model.CidadeDtoV2;
import br.dev.pauloroberto.algafood.domain.model.Cidade;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CidadeDtoAssemblerV2 extends RepresentationModelAssemblerSupport<Cidade, CidadeDtoV2> {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private LinkHelperV2 linkHelper;

    public CidadeDtoAssemblerV2() {
        super(CidadeControllerV2.class, CidadeDtoV2.class);
    }

    @Override
    public @NotNull CidadeDtoV2 toModel(@NotNull Cidade cidade) {
        CidadeDtoV2 cidadeDto = createModelWithId(cidade.getId(), cidade);
        modelMapper.map(cidade, cidadeDto);
        cidadeDto.add(linkHelper.linkToCidades("cidades"));

        return cidadeDto;
    }

    @Override
    public @NotNull CollectionModel<CidadeDtoV2> toCollectionModel(@NotNull Iterable<? extends Cidade> entities) {
        return super.toCollectionModel(entities)
                .add(linkHelper.linkToCidades());
    }

}
