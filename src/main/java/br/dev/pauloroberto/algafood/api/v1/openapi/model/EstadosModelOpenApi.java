package br.dev.pauloroberto.algafood.api.v1.openapi.model;

import br.dev.pauloroberto.algafood.api.v1.model.EstadoDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@ApiModel("EstadosModel")
@Data
public class EstadosModelOpenApi {

    private EstadosEmbeddedModelOpenApi _embedded;
    private LinksModelOpenApi _links;

    @ApiModel("EstadosEmbeddedModel")
    @Data
    public static class EstadosEmbeddedModelOpenApi {

        private List<EstadoDto> estados;

    }

}
