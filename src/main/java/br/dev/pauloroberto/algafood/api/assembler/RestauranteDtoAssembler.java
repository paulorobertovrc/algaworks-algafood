package br.dev.pauloroberto.algafood.api.assembler;

import br.dev.pauloroberto.algafood.api.controller.RestauranteController;
import br.dev.pauloroberto.algafood.api.helper.LinkHelper;
import br.dev.pauloroberto.algafood.api.model.RestauranteDto;
import br.dev.pauloroberto.algafood.api.model.input.RestauranteInputDto;
import br.dev.pauloroberto.algafood.domain.model.Restaurante;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteDtoAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteDto> {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private LinkHelper linkHelper;

    public RestauranteDtoAssembler() {
        super(RestauranteController.class, RestauranteDto.class);
    }

    @Override
    public @NotNull RestauranteDto toModel(@NotNull Restaurante restaurante) {
        RestauranteDto restauranteDto = modelMapper.map(restaurante, RestauranteDto.class);

        restauranteDto.add(linkHelper.linkToRestaurante(restauranteDto.getId()));
        restauranteDto.add(linkHelper.linkToRestaurantes("restaurantes"));
        restauranteDto.add(linkHelper.linkToCozinha(restauranteDto.getCozinha().getId()));
        restauranteDto.add(linkHelper.linkToRestauranteFormasPagamento(restauranteDto.getId(), "formas-pagamento"));
        restauranteDto.add(linkHelper.linkToResponsaveisRestaurante(restauranteDto.getId(), "responsaveis"));

        return restauranteDto;
    }

    @Override
    public @NotNull CollectionModel<RestauranteDto> toCollectionModel(
            @NotNull Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities).add(linkHelper.linkToRestaurantes());
    }

    public RestauranteInputDto toInputDto(Restaurante restaurante) {
        return modelMapper.map(restaurante, RestauranteInputDto.class);
    }

}
