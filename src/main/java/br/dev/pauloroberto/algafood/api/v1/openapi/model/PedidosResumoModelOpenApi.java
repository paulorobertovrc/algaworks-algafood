package br.dev.pauloroberto.algafood.api.v1.openapi.model;

import br.dev.pauloroberto.algafood.api.v1.model.PedidoResumoDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@ApiModel("PedidosResumoModel")
@Getter
@Setter
public class PedidosResumoModelOpenApi {

    private PedidosResumoEmbeddedModelOpenApi _embedded;
    private LinksModelOpenApi _links;
    private PageModelOpenApi page;

    @ApiModel("PedidosResumoEmbeddedModel")
    @Data
    private static class PedidosResumoEmbeddedModelOpenApi {
        private List<PedidoResumoDto> pedidos;
    }

}
