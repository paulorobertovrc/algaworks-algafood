package br.dev.pauloroberto.algafood.domain.repository;

import br.dev.pauloroberto.algafood.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestauranteRepository
        extends JpaRepository<Restaurante, Long>, RestauranteCustomizedRepository,
        JpaSpecificationExecutor<Restaurante>
{
    List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);
    List<Restaurante> consultarPorNome(String nome, Long cozinhaId);
    Optional<Restaurante> findFirstRestauranteByNomeContaining(String nome);
    List<Restaurante> findTop2ByNomeContaining(String nome);
    int countByCozinhaId(Long cozinhaId);
}
