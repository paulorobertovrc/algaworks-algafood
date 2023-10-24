package br.dev.pauloroberto.algafood.api.v1.openapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@ApiModel("Pageable")
public class PageableModelOpenApi {
    @ApiModelProperty(example = "0", value = "Número da página (começa em 0)")
    private int page;

    @ApiModelProperty(example = "10", value = "Quantidade de registros por página")
    private int size;

    @ApiModelProperty(example = "nome,asc", value = "Nome da propriedade e sentido para ordenação [asc, desc]. "
            + "Pode ser passado mais de um critério de ordenação separando por vírgula.")
    private List<String> sort;
}
