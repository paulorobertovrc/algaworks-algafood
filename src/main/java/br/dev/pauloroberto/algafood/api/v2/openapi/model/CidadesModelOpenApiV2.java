package br.dev.pauloroberto.algafood.api.v2.openapi.model;

import br.dev.pauloroberto.algafood.api.v2.model.CidadeDtoV2;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("CidadesModel")
@Data
public class CidadesModelOpenApiV2 {

    private CidadeEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("CidadesEmbeddedModel")
    @Data
    public static class CidadeEmbeddedModelOpenApi {

        private List<CidadeDtoV2> cidades;

    }

}
