package br.dev.pauloroberto.algafood.domain.repository;

import br.dev.pauloroberto.algafood.domain.model.Pedido;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, Long> {
    @Query("from Pedido p join fetch p.cliente join fetch p.restaurante r join fetch r.cozinha")
    // O uso do join fetch é para evitar o problema N+1
    List<Pedido> findAll();
}
