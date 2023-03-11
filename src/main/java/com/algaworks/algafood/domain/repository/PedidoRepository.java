package com.algaworks.algafood.domain.repository;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Pedido;

// JpaSpecificationExecutor é responsável pelos métodos que executam objetos specification
// estende a interface personalizada do Spring Data JPA, CustomJpaRepository
@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, Long>{}