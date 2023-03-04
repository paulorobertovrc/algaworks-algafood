package br.dev.pauloroberto.algafood.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoInputDto {
    @NotBlank
    public String nome;

    @NotBlank
    public String descricao;

    @NotNull
    @PositiveOrZero
    public BigDecimal preco;

    @NotNull
    public Boolean ativo;
}
