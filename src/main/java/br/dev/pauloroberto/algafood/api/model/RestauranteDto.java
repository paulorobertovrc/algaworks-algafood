package br.dev.pauloroberto.algafood.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class RestauranteDto {
    private Long id;
    private String nome;
    private BigDecimal taxaFrete;
    private CozinhaDto cozinha;
    private Boolean ativo;
}
