package com.algaworks.algafood.infrastructure.repository;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepositoryQuery;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Restaurante> find(
        String nome, 
        BigDecimal taxaFreteInicial, 
        BigDecimal taxaFreteFinal
    ) 
    {
        // ============== Exemplo de consulta simples com Criteria API =================

        // Não utiliza os parâmetros do método find, para fins didáticos do exemplo

        // constróio uma instância de Criteria a partir de EntityManager
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        // inicia uma query a partir de builder e atribui a CriteriaQuery tipada de Restaurante
        CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);

        // adiciona a cláusula "from" referenciando a entidade representada por Restaurante
        criteria.from(Restaurante.class);

        // instancia a query formada por criteria e atribui a um TypedQuery tipado de Restaurante
        TypedQuery<Restaurante> query = manager.createQuery(criteria);

        // retorna uma lista com o resultado da consulta
        return query.getResultList();
    }
    
}
