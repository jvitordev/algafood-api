package com.algaworks.algafood.infrastructure.repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepositoryQuery;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQuery {

    @Autowired
    private EntityManager manager;

    /*
     * Método utilizado por TesteController para retornar uma lista de restaurantes
     * que correspondam ao nome e faixa de taxa de frete desejados.
     * Se nenhum parâmetro é passado, retorna uma lista com todos os restaurantes cadastrados.
     */
    @Override
    public List<Restaurante> find(
        String nome, 
        BigDecimal taxaFreteInicial, 
        BigDecimal taxaFreteFinal
    ) 
    {
        /*
         * StringBuilder é instanciado para futura concatenação de strings com partes da
         * consulta JPQL
         */
        var jpql = new StringBuilder();

        /*
         * caso todos os parâmetros sejam nulos, serão listados todos os restaurantes a
         * primeira parte da consulta é adicionada ao objeto StringBuilder por meio de
         * ppend
         */
        jpql.append("from Restaurante where 0 = 0 ");

        /*
         * um HashMap é instanciado para auxiliar no uso de setParameter conforme os
         * parâmetros não nulos
         */
        var query = new HashMap<String, Object>();

        /*
         * se o nome é uma string de comprimento maior que zero ou é null,
         * ajuste a consulta e adicione uma chave e valor conforme o parâmetro
         */
        if (StringUtils.hasLength(nome)) {

            jpql.append("and nome like :nome ");
            query.put("nome", "%" + nome + "%");
        }

        /*
         * se o taxaFreteInicial é null,
         * ajuste a consulta e adicione uma chave e valor conforme o parâmetro
         */
        if (taxaFreteInicial != null) {

            jpql.append("and taxaFrete >= :taxaInicial ");
            query.put("taxaInicial", taxaFreteInicial);
        }

        /*
         * se o taxaFreteFinal é null,
         * ajuste a consulta e adicione uma chave e valor conforme o parâmetro
         */
        if (taxaFreteFinal != null) {

            jpql.append("and taxaFrete <= :taxaFinal");
            query.put("taxaFinal", taxaFreteFinal);
        }

        /*
         * instancia uma consulta tipada de Restaurante com EntityManager
         * para futura inserção de setParameter conforme parâmetros não nulos
         */
        TypedQuery<Restaurante> consulta =  manager.createQuery(
            jpql.toString(), 
            Restaurante.class
        );

        /*
         * adicona as chaves e os valores inseridos em query HashMap aos parâmetros de
         * setParameter
         */
        query.forEach((chave, valor) -> {

            consulta.setParameter(chave, valor);
        });

        /*
         * retorna uma lista de restaurantes encontrados no BD correspondentes aos
         * parâmetros
         * da consulta, se nada é encontrado, uma lista vazia é retornada
         */
        return consulta.getResultList();

    }
    
}
