package br.dev.pauloroberto.algafood.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class RestauranteIdInputDto {
    @NotNull
    @ApiModelProperty(example = "1", required = true)
    private Long id;
}
