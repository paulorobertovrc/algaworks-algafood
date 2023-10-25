package br.dev.pauloroberto.algafood.api.v2.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class CidadeInputDtoV2 {
    @NotBlank
    @ApiModelProperty(example = "Campo Grande", required = true)
    private String nomeCidade;

    @Valid
    @NotNull
    @ApiModelProperty(example = "1", required = true)
    private Long idEstado;
}
