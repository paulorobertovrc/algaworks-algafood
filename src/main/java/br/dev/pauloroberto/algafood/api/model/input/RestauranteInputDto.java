package br.dev.pauloroberto.algafood.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Setter
@Getter
public class RestauranteInputDto {
    @NotBlank
    @ApiModelProperty(example = "Thai Gourmet", required = true)
    private String nome;

    @PositiveOrZero
    @NotNull
    @ApiModelProperty(example = "10.00", required = true)
    private BigDecimal taxaFrete;

    @Valid
    @NotNull
    private CozinhaIdInputDto cozinha;

    @Valid
    @NotNull
    private EnderecoInputDto endereco;

}
