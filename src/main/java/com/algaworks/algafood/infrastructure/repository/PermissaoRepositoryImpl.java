package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;

@Repository
public class PermissaoRepositoryImpl implements PermissaoRepository {

    @PersistenceContext
    EntityManager manager;

    @Override
    public List<Permissao> todas() {

        return manager.createQuery(
            "from Permissao", 
            Permissao.class
            ).getResultList();
    }

    @Override
    public Permissao porId(Long id) {

        return manager.find(Permissao.class, id);
    }

    @Override
    @Transactional
    public Permissao adicionar(Permissao permissao) {
        
        return manager.merge(permissao);
    }

    @Override
    @Transactional
    public void remover(Permissao permissao) {
        
        permissao = porId(permissao.getId());
        manager.remove(permissao);
    }
    
}
