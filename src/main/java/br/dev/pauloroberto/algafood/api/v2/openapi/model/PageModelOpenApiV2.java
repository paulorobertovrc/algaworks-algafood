package br.dev.pauloroberto.algafood.api.v2.openapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("PageModel")
@Getter
@Setter
public class PageModelOpenApiV2 {

    @ApiModelProperty(example = "10", value = "Quantidade de registros por página")
    private int size;

    @ApiModelProperty(example = "50", value = "Total de registros")
    private long totalElements;

    @ApiModelProperty(example = "5", value = "Total de páginas")
    private int totalPages;

    @ApiModelProperty(example = "0", value = "Número da página (começa em 0)")
    private int number;

}
