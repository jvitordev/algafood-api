package com.algaworks.algafood.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

// Interface que estende JpaRepository, e por isso, seus métodos são acessíveis em todo o projeto
// Generics são usados para repassar os parâmetros esperados em JpaRepository
@NoRepositoryBean
public interface CustomJpaRepository<T, ID> extends JpaRepository<T, ID> {

    Optional<T> buscarPrimeiro();
}
