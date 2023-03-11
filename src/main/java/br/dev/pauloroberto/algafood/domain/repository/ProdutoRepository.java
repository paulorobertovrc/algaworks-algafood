package br.dev.pauloroberto.algafood.domain.repository;

import br.dev.pauloroberto.algafood.domain.model.Produto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends CustomJpaRepository<Produto, Long> {

//    @Query("from Produto p where p.restaurante.id = :restaurante and p.id = :produto")
//    Optional<Produto> findById(@Param("restaurante") Long restauranteId, @Param("produto") Long produtoId);
    Optional<Produto> findByRestauranteIdAndId(Long restauranteId, Long produtoId);

    List<Produto> findAllByRestauranteId(Long restauranteId);
    List<Produto> findAllByRestauranteIdAndAtivoTrue(Long restauranteId);
}
