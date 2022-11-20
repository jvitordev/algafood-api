package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;

@Component
public class CidadeRepositoryImpl implements CidadeRepository {

    @PersistenceContext
    EntityManager manager;

    @Override
    public List<Cidade> todas() {

        return manager.createQuery(
            "from Cidade", 
            Cidade.class)
            .getResultList();
    }

    @Override
    public Cidade porId(Long id) {
        Cidade cidade = manager.find(Cidade.class, id);

        if (cidade == null) {
            throw new EmptyResultDataAccessException(1);
        }

        return cidade;
    }

    @Override
    @Transactional
    public Cidade adicionar(Cidade cidade) {
        
        return manager.merge(cidade);
    }

    @Override
    @Transactional
    public void remover(Long id) {

        Cidade cidade = porId(id);

        if (cidade == null) {
            throw new EmptyResultDataAccessException(1);
        }
        
        manager.remove(cidade);
    }
    
}
