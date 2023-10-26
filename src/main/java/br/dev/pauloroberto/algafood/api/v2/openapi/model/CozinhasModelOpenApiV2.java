package br.dev.pauloroberto.algafood.api.v2.openapi.model;

import br.dev.pauloroberto.algafood.api.v2.model.CozinhaDtoV2;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("CozinhasModel")
@Setter
@Getter
public class CozinhasModelOpenApiV2 {

    private CozinhasEmbeddedModelOpenApi _embedded;
    private Links _links;
    private PageModelOpenApiV2 page;

    @ApiModel("CozinhasEmbeddedModel")
    @Data
    private static class CozinhasEmbeddedModelOpenApi {

        private List<CozinhaDtoV2> cozinhas;

    }

}
