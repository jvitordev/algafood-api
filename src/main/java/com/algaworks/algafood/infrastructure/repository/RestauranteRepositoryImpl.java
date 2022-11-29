package com.algaworks.algafood.infrastructure.repository;

import static com.algaworks.algafood.infrastructure.repository.spec.RestauranteSpecs.comFreteGratis;
import static com.algaworks.algafood.infrastructure.repository.spec.RestauranteSpecs.comNomeSemelhante;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepositoryQuery;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Autowired @Lazy
    RestauranteRepository restauranteRepository;

    // Consulta entidades de Restaurante que atendem aos filtros opcionais de nome e faixa de preço de frete
    // Controller mapeado: TesteController
    @Override
    public List<Restaurante> find(
        String nome, 
        BigDecimal taxaFreteInicial, 
        BigDecimal taxaFreteFinal
    ) 
    {
        // ===== Exemplo de consulta com Criteria API com filtros dinâmicos =====

        // constróio uma instância de Criteria a partir de EntityManager
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        // inicia uma query a partir de builder e atribui a CriteriaQuery tipada de Restaurante
        CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
        
        // adiciona a cláusula "from" referenciando a entidade representada por Restaurante e atribui à raiz Root
        Root <Restaurante> root = criteria.from(Restaurante.class);

        // inicia uma lista de Predicate
        var predicates = new ArrayList<Predicate>();

        // se nome não é null ou vazio, um Predicate que pesquisa um nome usando like é adicionado a lista
        if (StringUtils.hasLength(nome)) {
            predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));
        }

        // se taxaFreteInicial não é null, um Predicate que pesquisa entidades que são maior ou igual a taxaFreteInicial é adiconado a lista
        if (taxaFreteInicial != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial));
        }

        // se taxaFreteFinal não é null, um Predicate que pesquisa entidades que são menor ou igual a taxaFreteFinal é adiconado a lista
        if (taxaFreteFinal != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal));
        }

        // converte a lista em um array de Predicate e atribui a criteria.where
        criteria.where(predicates.toArray(new Predicate[0]));
        

        // instancia a query formada por criteria e atribui a um TypedQuery tipado de Restaurante
        TypedQuery<Restaurante> query = manager.createQuery(criteria);

        // retorna uma lista com o resultado da consulta
        return query.getResultList();
    }

    @Override
    public List<Restaurante> findComFreteGratis(String nome) {
        
        return restauranteRepository.findAll(comFreteGratis().and(comNomeSemelhante(nome)));
    }
    
}
