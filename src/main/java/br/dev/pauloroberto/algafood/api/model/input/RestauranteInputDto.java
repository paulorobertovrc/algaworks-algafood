package br.dev.pauloroberto.algafood.api.model.input;

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
    private String nome;

    @PositiveOrZero
    @NotNull
    private BigDecimal taxaFrete;

    @Valid
    @NotNull
    private CozinhaIdInputDto cozinha;

    @Valid
    @NotNull
    private EnderecoInputDto endereco;

}
