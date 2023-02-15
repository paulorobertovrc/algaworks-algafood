package br.dev.pauloroberto.algafood.infrastructure.repository;

import br.dev.pauloroberto.algafood.domain.model.Restaurante;
import br.dev.pauloroberto.algafood.domain.repository.RestauranteCustomizedRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class RestauranteRepositoryImpl implements RestauranteCustomizedRepository {
    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Restaurante> buscaCustomizada(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {


//    CONSULTA DINÂMICA COM JPQL

//        StringBuilder jpql = new StringBuilder();
//        jpql.append("from Restaurante where 0 = 0 ");
//
//        HashMap<String, Object> parametros = new HashMap<>();
//
//        if (nome != null && !nome.isBlank()) {
//            jpql.append("and nome like :nome ");
//            parametros.put("nome", "%" + nome + "%");
//        }
//
//        if (taxaFreteInicial != null) {
//            jpql.append("and taxaFrete >= :taxaInicial ");
//            parametros.put("taxaInicial", taxaFreteInicial);
//        }
//
//        if (taxaFreteFinal != null) {
//            jpql.append("and taxaFrete <= :taxaFinal ");
//            parametros.put("taxaFinal", taxaFreteFinal);
//        }
//
//        TypedQuery<Restaurante> query = manager.createQuery(jpql.toString(), Restaurante.class);
//
//        parametros.forEach(query::setParameter);
//
//        return query.getResultList();



//    CONSULTA DINÂMICA COM CRITERIA API

        CriteriaQuery<Restaurante> criteria = manager.getCriteriaBuilder().createQuery(Restaurante.class);
        Root<Restaurante> root = criteria.from(Restaurante.class);

        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(nome)) {
            predicates.add(manager.getCriteriaBuilder().like(root.get("nome"), "%" + nome + "%"));
        }

        if (taxaFreteInicial != null) {
            predicates.add(manager.getCriteriaBuilder().greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial));
        }

        if (taxaFreteFinal != null) {
            predicates.add(manager.getCriteriaBuilder().lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal));
        }

        criteria.where(predicates.toArray(new Predicate[0]));

        return manager.createQuery(criteria)
                .getResultList();
    }
}
