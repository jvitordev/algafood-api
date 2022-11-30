package com.algaworks.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Restaurante;

// JpaSpecificationExecutor é responsável pelos métodos que executam objetos specification
// estende a interface personalizada do Spring Data JPA, CustomJpaRepository
@Repository
public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQuery, JpaSpecificationExecutor<Restaurante> {
    // List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);
	
    // @Query("from Restaurante where nome like %:nome% and cozinha.id = :id")
    // List<Restaurante> procurarPorNome(String nome, @Param("id") Long cozinha);

    // List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long cozinha);

    Optional<Restaurante> findFirstByNomeContaining(String nome);

    List<Restaurante> findTop2ByNomeContaining(String nome);
}
