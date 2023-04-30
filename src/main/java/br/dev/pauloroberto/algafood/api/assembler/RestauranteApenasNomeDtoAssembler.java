package br.dev.pauloroberto.algafood.api.assembler;

import br.dev.pauloroberto.algafood.api.controller.RestauranteController;
import br.dev.pauloroberto.algafood.api.helper.LinkHelper;
import br.dev.pauloroberto.algafood.api.model.RestauranteApenasNomeDto;
import br.dev.pauloroberto.algafood.domain.model.Restaurante;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteApenasNomeDtoAssembler
        extends RepresentationModelAssemblerSupport<Restaurante, RestauranteApenasNomeDto> {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private LinkHelper linkHelper;

    public RestauranteApenasNomeDtoAssembler() {
        super(RestauranteController.class, RestauranteApenasNomeDto.class);
    }

    @Override
    public @NotNull RestauranteApenasNomeDto toModel(@NotNull Restaurante restaurante) {
        RestauranteApenasNomeDto restauranteApenasNomeDto = modelMapper.map(
                restaurante, RestauranteApenasNomeDto.class);

        restauranteApenasNomeDto.add(linkHelper.linkToRestaurante(restauranteApenasNomeDto.getId()));
        restauranteApenasNomeDto.add(linkHelper.linkToRestaurantes("restaurantes"));

        return restauranteApenasNomeDto;
    }

    @Override
    public @NotNull CollectionModel<RestauranteApenasNomeDto> toCollectionModel(
            @NotNull Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities).add(linkHelper.linkToRestaurantes());
    }

}
