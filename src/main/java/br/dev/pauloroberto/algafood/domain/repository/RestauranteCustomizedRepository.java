package br.dev.pauloroberto.algafood.domain.repository;

import br.dev.pauloroberto.algafood.domain.model.Restaurante;

import java.math.BigDecimal;
import java.util.List;

public interface RestauranteCustomizedRepository {
    List<Restaurante> buscaCustomizada(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);
}
