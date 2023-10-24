package br.dev.pauloroberto.algafood.api.v1.openapi.model;

import br.dev.pauloroberto.algafood.api.v1.model.FormaPagamentoDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@ApiModel("FormasPagamentoModel")
@Data
public class FormasPagamentoModelOpenApi {

    private FormasPagamentoEmbeddedModelOpenApi _embedded;
    private LinksModelOpenApi _links;

    @ApiModel("FormasPagamentoEmbeddedModel")
    @Data
    public static class FormasPagamentoEmbeddedModelOpenApi {

        private List<FormaPagamentoDto> formasPagamento;

    }

}
