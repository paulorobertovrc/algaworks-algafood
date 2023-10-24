package br.dev.pauloroberto.algafood.api.v1.openapi.model;

import br.dev.pauloroberto.algafood.api.v1.model.ProdutoDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@ApiModel("ProdutosModel")
@Data
public class ProdutosModelOpenApi {

    private ProdutosEmbeddedModelOpenApi _embedded;
    private LinksModelOpenApi _links;

    @ApiModel("ProdutosEmbeddedModel")
    @Data
    public static class ProdutosEmbeddedModelOpenApi {

        private List<ProdutoDto> produtos;

    }

}
