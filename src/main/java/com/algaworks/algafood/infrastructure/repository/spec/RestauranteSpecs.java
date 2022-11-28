package com.algaworks.algafood.infrastructure.repository.spec;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import com.algaworks.algafood.domain.model.Restaurante;

// Classe que reune todos os specifications da entidade Restaurante em métodos estáticos
public class RestauranteSpecs {
    
    // retorna uma specification de Restaurante com critério de frete = 0
    public static Specification<Restaurante> comFreteGratis() {

        /*
         * o lambda utiliza os parâmetros herdados da interface Specification para criar
         * e retornar um critério
         */
        return (root, query, builder) ->
        builder.equal(root.get("taxaFrete"), BigDecimal.ZERO);
    }

    // retorna uma specification de Restaurante com critério de nome que contenha dada string
    public static Specification<Restaurante> comNomeSemelhante(String nome) {
        return (root, query, builder) ->
        builder.like(root.get("nome"), "%" + nome + "%");
    }
}
