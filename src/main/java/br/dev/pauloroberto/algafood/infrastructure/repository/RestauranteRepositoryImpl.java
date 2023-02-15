package br.dev.pauloroberto.algafood.infrastructure.repository;

import br.dev.pauloroberto.algafood.domain.model.Restaurante;
import br.dev.pauloroberto.algafood.domain.repository.RestauranteCustomizedRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.List;

@Repository
public class RestauranteRepositoryImpl implements RestauranteCustomizedRepository {
    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Restaurante> buscaCustomizada(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
        var jpql = "from Restaurante where nome like :nome and taxaFrete between :taxaInicial and :taxaFinal";
        return manager.createQuery(jpql, Restaurante.class)
                .setParameter("nome", "%" + nome + "%")
                .setParameter("taxaInicial", taxaFreteInicial)
                .setParameter("taxaFinal", taxaFreteFinal)
                .getResultList();
    }
}
