package br.dev.pauloroberto.algafood.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class CidadeInputDto {
    @NotBlank
    @ApiModelProperty(example = "São Paulo", required = true)
    private String nome;
    @Valid
    @NotNull
    private EstadoIdInputDto estado;
}
