package com.algaworks.algafood.infrastructure.repository.spec;

import java.math.BigDecimal;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import com.algaworks.algafood.domain.model.Restaurante;

/* Classe que implementa o padrão Specification do DDD.
 * Specification de Restaurante para busca de restaurantes com frete grátis
*/
public class RestauranteComFreteGratisSpec implements Specification<Restaurante> {

    // Retorna um Predicate que tem como critério buscar no BD restaurantes com taxa de frete = 0
    @Override
    @Nullable
    public Predicate toPredicate(Root<Restaurante> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        return builder.equal(root.get("taxaFrete"), BigDecimal.ZERO);
    }
    
}
