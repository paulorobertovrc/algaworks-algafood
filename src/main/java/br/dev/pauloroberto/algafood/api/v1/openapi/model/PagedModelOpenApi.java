package br.dev.pauloroberto.algafood.api.v1.openapi.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PagedModelOpenApi<T> {
    @ApiModelProperty("Lista de cozinhas")
    private List<T> content;

    @ApiModelProperty(example = "10", value = "Quantidade de elementos por página")
    private Long size;

    @ApiModelProperty(example = "50", value = "Total de elementos")
    private Long totalElements;

    @ApiModelProperty(example = "5", value = "Total de páginas")
    private Long totalPages;

    @ApiModelProperty(example = "0", value = "Número da página (começa em 0)")
    private Long number;
}
