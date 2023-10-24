package br.dev.pauloroberto.algafood.api.v1.assembler;

import br.dev.pauloroberto.algafood.api.v1.controller.RestauranteController;
import br.dev.pauloroberto.algafood.api.v1.helper.LinkHelper;
import br.dev.pauloroberto.algafood.api.v1.model.RestauranteDto;
import br.dev.pauloroberto.algafood.api.v1.model.input.RestauranteInputDto;
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
        RestauranteDto restauranteDto = createModelWithId(restaurante.getId(), restaurante);
        modelMapper.map(restaurante, restauranteDto);

        restauranteDto.add(linkHelper.linkToRestaurantes("restaurantes"));

        if (restaurante.ativacaoPermitida()) {
            restauranteDto.add(linkHelper.linkToRestauranteAtivacao(restauranteDto.getId(), "ativar"));
        }

        if (restaurante.inativacaoPermitida()) {
            restauranteDto.add(linkHelper.linkToRestauranteInativacao(restauranteDto.getId(), "inativar"));
        }

        if (restaurante.aberturaPermitida()) {
            restauranteDto.add(linkHelper.linkToRestauranteAbertura(restauranteDto.getId(), "abrir"));
        }

        if (restaurante.fechamentoPermitido()) {
            restauranteDto.add(linkHelper.linkToRestauranteFechamento(restauranteDto.getId(), "fechar"));
        }

        restauranteDto.add(linkHelper.linkToProdutos(restauranteDto.getId(), "produtos"));
        restauranteDto.getCozinha().add(linkHelper.linkToCozinha(restauranteDto.getCozinha().getId()));

        if (restauranteDto.getEndereco() != null && restauranteDto.getEndereco().getCidade() != null) {
            restauranteDto.getEndereco().getCidade().add(
                    linkHelper.linkToCidade(restauranteDto.getEndereco().getCidade().getId()));
        }

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
