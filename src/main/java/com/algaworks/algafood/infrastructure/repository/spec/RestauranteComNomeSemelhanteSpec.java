package com.algaworks.algafood.infrastructure.repository.spec;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import com.algaworks.algafood.domain.model.Restaurante;

import lombok.AllArgsConstructor;

/* Classe que implementa o padrão Specification do DDD.
 * Specification de Restaurante para busca de restaurantes por nome
*/
@AllArgsConstructor
public class RestauranteComNomeSemelhanteSpec implements Specification<Restaurante> {

    private String nome;

    /* Retorna um Predicate que tem como critério buscar restaurantes cujo nome contém 
     * dada string.
    */
    @Override
    @Nullable
    public Predicate toPredicate(Root<Restaurante> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        return builder.like(root.get("nome"), "%" + nome + "%");
    }
    
}
