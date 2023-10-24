package br.dev.pauloroberto.algafood.api.v1.model.input;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(example = "Pizza de Calabresa", required = true)
    public String nome;

    @NotBlank
    @ApiModelProperty(example = "Feita com molho de tomates italianos, mussarela, calabresa e orégano", required = true)
    public String descricao;

    @NotNull
    @PositiveOrZero
    @ApiModelProperty(example = "50.00", required = true)
    public BigDecimal preco;

    @NotNull
    @ApiModelProperty(example = "true", required = true)
    public Boolean ativo;
}
