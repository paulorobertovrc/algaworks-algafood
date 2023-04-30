package br.dev.pauloroberto.algafood.api.assembler;

import br.dev.pauloroberto.algafood.api.controller.RestauranteController;
import br.dev.pauloroberto.algafood.api.helper.LinkHelper;
import br.dev.pauloroberto.algafood.api.model.RestauranteBasicoDto;
import br.dev.pauloroberto.algafood.domain.model.Restaurante;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteBasicoDtoAssembler
        extends RepresentationModelAssemblerSupport<Restaurante, RestauranteBasicoDto> {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private LinkHelper linkHelper;

    public RestauranteBasicoDtoAssembler() {
        super(RestauranteController.class, RestauranteBasicoDto.class);
    }

    @Override
    public @NotNull RestauranteBasicoDto toModel(@NotNull Restaurante restaurante) {
        RestauranteBasicoDto restauranteDto = modelMapper.map(restaurante, RestauranteBasicoDto.class);

        restauranteDto.add(linkHelper.linkToRestaurante(restauranteDto.getId()));
        restauranteDto.add(linkHelper.linkToRestaurantes("restaurantes"));
        restauranteDto.getCozinha().add(linkHelper.linkToCozinha(restauranteDto.getCozinha().getId()));

        return restauranteDto;
    }

    @Override
    public @NotNull CollectionModel<RestauranteBasicoDto> toCollectionModel(
            @NotNull Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities).add(linkHelper.linkToRestaurantes());
    }

}
