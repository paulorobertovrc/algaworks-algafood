package br.dev.pauloroberto.algafood.api.v1.assembler;

import br.dev.pauloroberto.algafood.api.v1.controller.CidadeController;
import br.dev.pauloroberto.algafood.api.v1.helper.LinkHelper;
import br.dev.pauloroberto.algafood.api.v1.model.CidadeDto;
import br.dev.pauloroberto.algafood.domain.model.Cidade;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CidadeDtoAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeDto> {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private LinkHelper linkHelper;

    public CidadeDtoAssembler() {
        super(CidadeController.class, CidadeDto.class);
    }

    @Override
    public @NotNull CidadeDto toModel(@NotNull Cidade cidade) {
        CidadeDto cidadeDto = modelMapper.map(cidade, CidadeDto.class);

        cidadeDto.add(linkHelper.linkToCidade(cidadeDto.getId()));
        cidadeDto.add(linkHelper.linkToCidades("cidades"));
        cidadeDto.getEstado().add(linkHelper.linkToEstado(cidadeDto.getEstado().getId()));

        return cidadeDto;
    }

    @Override
    public @NotNull CollectionModel<CidadeDto> toCollectionModel(@NotNull Iterable<? extends Cidade> entities) {
        return super.toCollectionModel(entities)
                .add(linkHelper.linkToCidades());
    }

}
