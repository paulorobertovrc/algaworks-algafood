package br.dev.pauloroberto.algafood.api.v1.openapi.model;

import br.dev.pauloroberto.algafood.api.v1.model.RestauranteBasicoDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@ApiModel("RestaurantesBasicoModel")
@Data
public class RestaurantesBasicoModelOpenApi {

    private RestaurantesEmbeddedModelOpenApi _embedded;
    private LinksModelOpenApi _links;

    @ApiModel("RestaurantesEmbeddedModel")
    @Data
    public static class RestaurantesEmbeddedModelOpenApi {

        private List<RestauranteBasicoDto> restaurantes;

    }

}
