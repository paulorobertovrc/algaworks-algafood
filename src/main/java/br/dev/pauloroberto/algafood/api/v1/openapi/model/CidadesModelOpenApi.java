package br.dev.pauloroberto.algafood.api.v1.openapi.model;

import br.dev.pauloroberto.algafood.api.v1.model.CidadeDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("CidadesModel")
@Data
public class CidadesModelOpenApi {

    private CidadeEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("CidadesEmbeddedModel")
    @Data
    public static class CidadeEmbeddedModelOpenApi {

        private List<CidadeDto> cidades;

    }

}
