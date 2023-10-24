package br.dev.pauloroberto.algafood.api.v1.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class CidadeIdInputDto {
    @NotNull
    @ApiModelProperty(example = "1", required = true)
    private Long id;

}
