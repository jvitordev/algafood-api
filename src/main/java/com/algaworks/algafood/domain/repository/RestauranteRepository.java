package com.algaworks.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Restaurante;

// JpaSpecificationExecutor é responsável pelos métodos que executam objetos specification
// estende a interface personalizada do Spring Data JPA, CustomJpaRepository
@Repository
public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQuery, JpaSpecificationExecutor<Restaurante> {
    
    @Query("from Restaurante r left join fetch r.cozinha")
    List<Restaurante> findAll();

    Optional<Restaurante> findFirstByNomeContaining(String nome);

    List<Restaurante> findTop2ByNomeContaining(String nome);
}
