package br.dev.pauloroberto.algafood.api.v1.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class FormaPagamentoInputDto {
    @NotBlank
    @ApiModelProperty(example = "Cartão de crédito", required = true)
    private String descricao;

}
