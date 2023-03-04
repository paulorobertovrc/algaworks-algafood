package br.dev.pauloroberto.algafood.domain.repository;

import br.dev.pauloroberto.algafood.domain.model.Produto;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends CustomJpaRepository<Produto, Long> {
    List<Produto> findAllByRestauranteId(Long restauranteId);
    Optional<Produto> findByRestauranteIdAndId(Long restauranteId, Long produtoId);
}
